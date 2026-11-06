package band.effective.office.backend.feature.board.core.domain.model

/**
 * Meeting state (what the tablet shows to the participant).
 */
enum class MeetingStatus {
    /** Meeting created, participants are connecting */
    WAITING,

    /** Voting is open for the current question */
    VOTING_OPEN,

    /** Voting for the question is closed, result is shown */
    QUESTION_CLOSED,

    /** Meeting finished, individual answers deleted */
    FINISHED
}

/**
 * Agenda question state.
 */
enum class QuestionStatus {
    /** Question has not been considered yet */
    PENDING,

    /** Voting is open for this question */
    OPEN,

    /** Voting for this question is closed */
    CLOSED
}
