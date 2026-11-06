package band.effective.office.backend.feature.board.core.controller

import band.effective.office.backend.feature.board.core.dto.BoardDtoMapper
import band.effective.office.backend.feature.board.core.dto.ClaimParticipantRequest
import band.effective.office.backend.feature.board.core.dto.CreateMeetingRequest
import band.effective.office.backend.feature.board.core.dto.CurrentStateDTO
import band.effective.office.backend.feature.board.core.dto.MeetingDTO
import band.effective.office.backend.feature.board.core.dto.MeetingDetailsDTO
import band.effective.office.backend.feature.board.core.dto.MeetingResultsDTO
import band.effective.office.backend.feature.board.core.dto.ParticipantDTO
import band.effective.office.backend.feature.board.core.dto.VoteProgressDTO
import band.effective.office.backend.feature.board.core.dto.VoteRequest
import band.effective.office.backend.feature.board.core.service.BoardService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller of the board of directors module.
 * Meeting preparation, participant authorization, voting, results.
 */
@RestController
@RequestMapping("/v1/board")
@Tag(name = "Board", description = "Board of directors module: meetings and voting")
class BoardController(
    private val boardService: BoardService
) {

    /** Checks that the module is wired in and the server responds. */
    @GetMapping("/ping")
    @Operation(summary = "Ping", description = "Module availability check")
    fun ping(): ResponseEntity<Map<String, String>> =
        ResponseEntity.ok(mapOf("status" to "ok"))

    // ---------- Preparation (secretary) ----------

    @PostMapping("/meetings")
    @Operation(summary = "Create a meeting", description = "Title, date, participants and agenda questions")
    fun createMeeting(@RequestBody request: CreateMeetingRequest): ResponseEntity<MeetingDetailsDTO> {
        val meeting = boardService.createMeeting(request)
        return ResponseEntity.ok(BoardDtoMapper.toMeetingDetailsDTO(meeting))
    }

    @GetMapping("/meetings")
    @Operation(summary = "List meetings")
    fun getMeetings(): ResponseEntity<List<MeetingDTO>> =
        ResponseEntity.ok(BoardDtoMapper.toMeetingDTOList(boardService.getMeetings()))

    @GetMapping("/meetings/{meetingId}")
    @Operation(summary = "Meeting by id", description = "Full details: questions, quorum, decision rules")
    fun getMeeting(@PathVariable meetingId: Long): ResponseEntity<MeetingDetailsDTO> =
        ResponseEntity.ok(BoardDtoMapper.toMeetingDetailsDTO(boardService.getMeeting(meetingId)))

    // ---------- Participant authorization (tablets) ----------

    @GetMapping("/meetings/{meetingId}/participants")
    @Operation(summary = "List participants", description = "The tablet fetches names for selection; taken names are marked claimed=true")
    fun getParticipants(@PathVariable meetingId: Long): ResponseEntity<List<ParticipantDTO>> =
        ResponseEntity.ok(boardService.getParticipants(meetingId))

    @PostMapping("/meetings/{meetingId}/participants/claim")
    @Operation(summary = "Claim a name", description = "The tablet authorizes under a participant name. A taken name results in a 409 error")
    fun claimParticipant(
        @PathVariable meetingId: Long,
        @RequestBody request: ClaimParticipantRequest
    ): ResponseEntity<ParticipantDTO> =
        ResponseEntity.ok(boardService.claimParticipant(meetingId, request))

    @PostMapping("/meetings/{meetingId}/participants/{participantId}/release")
    @Operation(summary = "Release a name", description = "The secretary resets a single name claim (e.g. a participant picked the wrong name). Other tablets are unaffected")
    fun releaseParticipant(
        @PathVariable meetingId: Long,
        @PathVariable participantId: Long
    ): ResponseEntity<Map<String, String>> {
        boardService.releaseParticipant(meetingId, participantId)
        return ResponseEntity.ok(mapOf("status" to "released"))
    }

    // ---------- Voting ----------

    @PostMapping("/meetings/{meetingId}/questions/{questionNumber}/open")
    @Operation(summary = "Open voting", description = "The secretary opens voting for a question (agenda number)")
    fun openVoting(
        @PathVariable meetingId: Long,
        @PathVariable questionNumber: Int
    ): ResponseEntity<Map<String, String>> {
        boardService.openVoting(meetingId, questionNumber)
        return ResponseEntity.ok(mapOf("status" to "open"))
    }

    @GetMapping("/meetings/{meetingId}/current")
    @Operation(summary = "Current state", description = "The tablet polls the server: waiting / voting in progress / result / finished")
    fun getCurrentState(@PathVariable meetingId: Long): ResponseEntity<CurrentStateDTO> =
        ResponseEntity.ok(boardService.getCurrentState(meetingId))

    @PostMapping("/meetings/{meetingId}/questions/{questionNumber}/vote")
    @Operation(summary = "Vote", description = "A participant submits an answer for the open question. A duplicate vote is rejected")
    fun vote(
        @PathVariable meetingId: Long,
        @PathVariable questionNumber: Int,
        @RequestBody request: VoteRequest
    ): ResponseEntity<Map<String, String>> {
        boardService.vote(meetingId, questionNumber, request)
        return ResponseEntity.ok(mapOf("status" to "accepted"))
    }

    @GetMapping("/meetings/{meetingId}/questions/{questionNumber}/progress")
    @Operation(summary = "Voting progress", description = "How many participants answered out of the total (for the admin app)")
    fun getProgress(
        @PathVariable meetingId: Long,
        @PathVariable questionNumber: Int
    ): ResponseEntity<VoteProgressDTO> =
        ResponseEntity.ok(boardService.getProgress(meetingId, questionNumber))

    @PostMapping("/meetings/{meetingId}/questions/{questionNumber}/close")
    @Operation(summary = "Close voting", description = "The secretary closes voting, including without waiting for the rest")
    fun closeVoting(
        @PathVariable meetingId: Long,
        @PathVariable questionNumber: Int
    ): ResponseEntity<Map<String, String>> {
        boardService.closeVoting(meetingId, questionNumber)
        return ResponseEntity.ok(mapOf("status" to "closed"))
    }

    // ---------- Results ----------

    @GetMapping("/meetings/{meetingId}/results")
    @Operation(summary = "Meeting results", description = "Aggregated results across all questions, no individual votes")
    fun getResults(@PathVariable meetingId: Long): ResponseEntity<MeetingResultsDTO> =
        ResponseEntity.ok(boardService.getResults(meetingId))

    @PostMapping("/meetings/{meetingId}/finish")
    @Operation(summary = "Finish meeting", description = "Deletes individual votes and tablet claims; returns the final results")
    fun finishMeeting(@PathVariable meetingId: Long): ResponseEntity<MeetingResultsDTO> =
        ResponseEntity.ok(boardService.finishMeeting(meetingId))
}
