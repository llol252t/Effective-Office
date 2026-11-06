package band.effective.office.backend.feature.board.core.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Participant vote")
data class VoteRequest(
    @Schema(description = "Participant identifier (the tablet knows its own after authorization)", example = "3")
    val participantId: Long,

    @Schema(description = "Selected answer", example = "For")
    val answer: String
)
