package band.effective.office.backend.feature.board.core.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Voting progress for a question (for the admin app)")
data class VoteProgressDTO(
    @Schema(description = "How many participants have answered", example = "8")
    val answered: Int,

    @Schema(description = "Total number of participants", example = "10")
    val total: Int
)

@Schema(description = "Aggregated result for a single question")
data class QuestionResultDTO(
    @Schema(description = "Question identifier")
    val questionId: Long,

    @Schema(description = "Ordinal number in the agenda")
    val number: Int,

    @Schema(description = "Question text")
    val text: String,

    @Schema(description = "Votes per option: option -> count")
    val counts: Map<String, Int>,

    @Schema(description = "How many participants have answered")
    val answered: Int,

    @Schema(description = "Quorum reached")
    val quorumReached: Boolean,

    @Schema(description = "Decision accepted")
    val accepted: Boolean,

    @Schema(description = "Winning option (for SINGLE_CHOICE)")
    val winner: String? = null
)

@Schema(description = "Meeting results across all questions")
data class MeetingResultsDTO(
    @Schema(description = "Meeting identifier")
    val meetingId: Long,

    @Schema(description = "Meeting is finished")
    val finished: Boolean,

    @Schema(description = "Results per question")
    val results: List<QuestionResultDTO>
)
