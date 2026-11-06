package band.effective.office.backend.feature.board.core.dto

import band.effective.office.backend.feature.board.core.domain.model.MeetingStatus
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Current meeting state for the tablet (polling)")
data class CurrentStateDTO(
    @Schema(description = "Meeting state", example = "VOTING_OPEN")
    val status: MeetingStatus,

    @Schema(description = "Currently open question (when status is VOTING_OPEN)")
    val currentQuestion: QuestionDTO? = null
)
