package band.effective.office.backend.feature.board.core.dto

import band.effective.office.backend.feature.board.core.domain.model.DecisionRule
import band.effective.office.backend.feature.board.core.domain.model.VotingType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Meeting creation request")
data class CreateMeetingRequest(
    @Schema(description = "Meeting title", example = "Board of directors meeting #1")
    val title: String,

    @Schema(description = "Date and time", example = "October 20, 2026, 11:00")
    val date: String,

    @Schema(description = "Participant names (in the order shown on the tablets)")
    val participants: List<String>,

    @Schema(description = "Agenda questions")
    val questions: List<CreateQuestionRequest>
)

@Schema(description = "Agenda question at meeting creation")
data class CreateQuestionRequest(
    @Schema(description = "Question text", example = "Approve the annual budget")
    val text: String,

    @Schema(description = "Voting type", example = "FOR_AGAINST_ABSTAIN")
    val type: VotingType,

    @Schema(description = "Answer options (SINGLE_CHOICE only)")
    val options: List<String> = emptyList(),

    @Schema(description = "Minimum quorum, participants", example = "7")
    val quorum: Int = 1,

    @Schema(description = "Decision rule", example = "SIMPLE_MAJORITY")
    val rule: DecisionRule = DecisionRule.SIMPLE_MAJORITY
)
