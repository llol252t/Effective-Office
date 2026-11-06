package band.effective.office.backend.feature.board.core.dto

import band.effective.office.backend.feature.board.core.domain.model.DecisionRule
import band.effective.office.backend.feature.board.core.domain.model.VotingType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Full meeting details (for the tablet and the admin app)")
data class MeetingDetailsDTO(
    @Schema(description = "Meeting identifier", example = "1")
    val id: Long,

    @Schema(description = "Meeting title")
    val title: String,

    @Schema(description = "Date and time")
    val date: String,

    @Schema(description = "Agenda questions")
    val questions: List<QuestionDTO>
)

@Schema(description = "Agenda question")
data class QuestionDTO(
    @Schema(description = "Question identifier", example = "1")
    val id: Long,

    @Schema(description = "Ordinal number in the agenda", example = "1")
    val number: Int,

    @Schema(description = "Question text")
    val text: String,

    @Schema(description = "Voting type")
    val type: VotingType,

    @Schema(description = "Answer options (for SINGLE_CHOICE)")
    val options: List<String>,

    @Schema(description = "Minimum quorum")
    val quorum: Int,

    @Schema(description = "Decision rule")
    val rule: DecisionRule
)
