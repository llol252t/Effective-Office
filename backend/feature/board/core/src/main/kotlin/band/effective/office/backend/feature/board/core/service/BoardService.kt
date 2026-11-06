package band.effective.office.backend.feature.board.core.service

import band.effective.office.backend.feature.board.core.domain.DecisionCalculator
import band.effective.office.backend.feature.board.core.domain.model.Meeting
import band.effective.office.backend.feature.board.core.domain.model.MeetingStatus
import band.effective.office.backend.feature.board.core.domain.model.Participant
import band.effective.office.backend.feature.board.core.domain.model.Question
import band.effective.office.backend.feature.board.core.domain.model.QuestionStatus
import band.effective.office.backend.feature.board.core.dto.ClaimParticipantRequest
import band.effective.office.backend.feature.board.core.dto.CreateMeetingRequest
import band.effective.office.backend.feature.board.core.dto.CurrentStateDTO
import band.effective.office.backend.feature.board.core.dto.MeetingResultsDTO
import band.effective.office.backend.feature.board.core.dto.ParticipantDTO
import band.effective.office.backend.feature.board.core.dto.QuestionResultDTO
import band.effective.office.backend.feature.board.core.dto.VoteProgressDTO
import band.effective.office.backend.feature.board.core.dto.VoteRequest
import band.effective.office.backend.feature.board.core.exception.InvalidBoardRequestException
import band.effective.office.backend.feature.board.core.exception.MeetingNotFoundException
import band.effective.office.backend.feature.board.core.exception.ParticipantAlreadyClaimedException
import band.effective.office.backend.feature.board.core.exception.VotingNotOpenException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

/**
 * Board of directors module service.
 *
 * All data lives only in the server memory (security requirement:
 * after the meeting is finished, individual answers are deleted).
 * State is split: the meeting itself is immutable, while the meeting
 * progress (current question, votes, claimed names) lives in [MeetingState].
 */
@Service
class BoardService {

    private val logger = LoggerFactory.getLogger(BoardService::class.java)

    private val idSequence = AtomicLong(1)

    /** Internal mutable state of a single meeting. */
    private class MeetingState(
        val meeting: Meeting,
        /** participant -> whether their name is claimed by some tablet */
        val claims: MutableMap<Long, Boolean>,
        /** index of the currently open question in the agenda, null when no voting is in progress */
        var currentQuestionIndex: Int? = null,
        /** status of each question */
        val questionStatuses: MutableMap<Long, QuestionStatus>,
        /** votes: question id -> (participant id -> answer) */
        val votes: MutableMap<Long, MutableMap<Long, String>> = mutableMapOf(),
        var finished: Boolean = false
    )

    private val meetings = mutableMapOf<Long, MeetingState>()

    // ---------- Meeting preparation (secretary) ----------

    /** Creates a meeting with questions and participants. Returns the created meeting. */
    @Synchronized
    fun createMeeting(request: CreateMeetingRequest): Meeting {
        if (request.participants.isEmpty() || request.questions.isEmpty()) {
            throw InvalidBoardRequestException("Meeting must have participants and questions")
        }
        val meetingId = idSequence.getAndIncrement()
        var questionId = idSequence.getAndIncrement()

        val questions = request.questions.map { q ->
            if (q.type.name == "SINGLE_CHOICE" && q.options.size < 2) {
                throw InvalidBoardRequestException("Single-choice question needs at least 2 options")
            }
            Question(
                id = questionId++,
                text = q.text,
                type = q.type,
                options = q.options,
                quorum = q.quorum,
                rule = q.rule
            )
        }
        val meeting = Meeting(
            id = meetingId,
            title = request.title,
            date = request.date,
            participantsCount = request.participants.size,
            questions = questions
        )

        val state = MeetingState(
            meeting = meeting,
            claims = request.participants.indices.associate { index ->
                (idSequence.getAndIncrement()) to false
            }.toMutableMap(),
            // participant names are stored separately, in list order
            questionStatuses = questions.associate { it.id to QuestionStatus.PENDING }.toMutableMap()
        )
        // store participant names in the same order as their ids
        stateParticipants[state] = request.participants.toList()
        meetings[meetingId] = state

        logger.info("Created meeting {} '{}' with {} questions and {} participants",
            meetingId, meeting.title, questions.size, request.participants.size)
        return meeting
    }

    /** Participant names per meeting state (the claims keys are their ids). */
    private val stateParticipants = mutableMapOf<MeetingState, List<String>>()

    /** Returns a meeting by id or throws MeetingNotFoundException. */
    fun getMeeting(meetingId: Long): Meeting =
        meetings[meetingId]?.meeting ?: throw MeetingNotFoundException("Meeting $meetingId not found")

    /** Returns the list of meetings. */
    fun getMeetings(): List<Meeting> = meetings.values.map { it.meeting }

    // ---------- Participant authorization (tablets) ----------

    /** List of participants with a "name claimed" flag (for the selection screen on the tablet). */
    fun getParticipants(meetingId: Long): List<ParticipantDTO> {
        val state = getState(meetingId)
        val names = stateParticipants[state] ?: emptyList()
        return state.claims.keys.sorted().mapIndexed { index, participantId ->
            ParticipantDTO(
                id = participantId,
                name = names.getOrElse(index) { "Participant ${index + 1}" },
                claimed = state.claims[participantId] == true
            )
        }
    }

    /**
     * A tablet claims a participant name.
     * Atomic: if the name is already taken, throws ParticipantAlreadyClaimedException.
     */
    @Synchronized
    fun claimParticipant(meetingId: Long, request: ClaimParticipantRequest): ParticipantDTO {
        val state = getState(meetingId)
        val participantId = request.participantId
        if (!state.claims.containsKey(participantId)) {
            throw InvalidBoardRequestException("Unknown participant $participantId")
        }
        if (state.claims[participantId] == true) {
            throw ParticipantAlreadyClaimedException("Participant $participantId already claimed")
        }
        state.claims[participantId] = true
        logger.info("Meeting {}: participant {} claimed", meetingId, participantId)
        return getParticipants(meetingId).first { it.id == participantId }
    }

    // ---------- Voting ----------

    /** Opens voting for a question (by its agenda number, starting from 1). */
    @Synchronized
    fun openVoting(meetingId: Long, questionNumber: Int) {
        val state = getState(meetingId)
        if (state.finished) throw InvalidBoardRequestException("Meeting is finished")
        if (state.currentQuestionIndex != null) throw InvalidBoardRequestException("Another voting is already open")
        val question = questionByNumber(state, questionNumber)
        if (state.questionStatuses[question.id] != QuestionStatus.PENDING) {
            throw InvalidBoardRequestException("Question $questionNumber was already voted")
        }
        state.questionStatuses[question.id] = QuestionStatus.OPEN
        state.currentQuestionIndex = questionNumber - 1
        logger.info("Meeting {}: voting opened for question {}", meetingId, questionNumber)
    }

    /** Current meeting state for the tablet (polling). */
    fun getCurrentState(meetingId: Long): CurrentStateDTO {
        val state = getState(meetingId)
        val status = when {
            state.finished -> MeetingStatus.FINISHED
            state.currentQuestionIndex != null -> MeetingStatus.VOTING_OPEN
            state.questionStatuses.values.any { it == QuestionStatus.CLOSED } -> MeetingStatus.QUESTION_CLOSED
            else -> MeetingStatus.WAITING
        }
        val current = state.currentQuestionIndex?.let { state.meeting.questions[it] }
        return CurrentStateDTO(status = status, currentQuestion = current?.let {
            band.effective.office.backend.feature.board.core.dto.QuestionDTO(
                id = it.id, number = state.currentQuestionIndex!! + 1, text = it.text,
                type = it.type, options = it.options, quorum = it.quorum, rule = it.rule
            )
        })
    }

    /** Accepts a participant's vote for the currently open question. */
    @Synchronized
    fun vote(meetingId: Long, questionNumber: Int, request: VoteRequest) {
        val state = getState(meetingId)
        val question = questionByNumber(state, questionNumber)
        if (state.questionStatuses[question.id] != QuestionStatus.OPEN) {
            throw VotingNotOpenException("Voting is not open for question $questionNumber")
        }
        if (state.claims[request.participantId] != true) {
            throw InvalidBoardRequestException("Participant ${request.participantId} is not authorized")
        }
        val questionVotes = state.votes.getOrPut(question.id) { mutableMapOf() }
        if (questionVotes.containsKey(request.participantId)) {
            throw InvalidBoardRequestException("Participant ${request.participantId} already voted")
        }
        questionVotes[request.participantId] = request.answer
        logger.info("Meeting {}: vote accepted for question {}", meetingId, questionNumber)
    }

    /** Voting progress: "N of M answered". */
    fun getProgress(meetingId: Long, questionNumber: Int): VoteProgressDTO {
        val state = getState(meetingId)
        val question = questionByNumber(state, questionNumber)
        val answered = state.votes[question.id]?.size ?: 0
        return VoteProgressDTO(answered = answered, total = state.claims.size)
    }

    /** Closes voting for a question (including "without waiting for the rest"). */
    @Synchronized
    fun closeVoting(meetingId: Long, questionNumber: Int) {
        val state = getState(meetingId)
        val question = questionByNumber(state, questionNumber)
        if (state.questionStatuses[question.id] != QuestionStatus.OPEN) {
            throw VotingNotOpenException("Voting is not open for question $questionNumber")
        }
        state.questionStatuses[question.id] = QuestionStatus.CLOSED
        state.currentQuestionIndex = null
        logger.info("Meeting {}: voting closed for question {}", meetingId, questionNumber)
    }

    // ---------- Results ----------

    /** Aggregated results across all questions (no individual votes disclosed). */
    fun getResults(meetingId: Long): MeetingResultsDTO {
        val state = getState(meetingId)
        val results = state.meeting.questions.mapIndexed { index, question ->
            val answers = state.votes[question.id]?.values?.toList() ?: emptyList()
            QuestionResultDTO(
                questionId = question.id,
                number = index + 1,
                text = question.text,
                counts = DecisionCalculator.countVotes(question, answers),
                answered = answers.size,
                quorumReached = DecisionCalculator.hasQuorum(question, answers.size),
                accepted = DecisionCalculator.isAccepted(question, answers),
                winner = DecisionCalculator.winner(question, answers)
            )
        }
        return MeetingResultsDTO(meetingId = meetingId, finished = state.finished, results = results)
    }

    /**
     * Finishes the meeting: deletes individual votes and tablet claims.
     * Only the aggregated results remain (the secretary recorded them in the minutes).
     */
    @Synchronized
    fun finishMeeting(meetingId: Long): MeetingResultsDTO {
        val state = getState(meetingId)
        val results = getResults(meetingId)
        state.votes.clear()
        state.claims.replaceAll { _, _ -> false }
        state.finished = true
        logger.info("Meeting {}: finished, individual votes deleted", meetingId)
        return results
    }

    // ---------- Helpers ----------

    private fun getState(meetingId: Long): MeetingState =
        meetings[meetingId] ?: throw MeetingNotFoundException("Meeting $meetingId not found")

    private fun questionByNumber(state: MeetingState, questionNumber: Int): Question =
        state.meeting.questions.getOrNull(questionNumber - 1)
            ?: throw InvalidBoardRequestException("No question number $questionNumber")
}
