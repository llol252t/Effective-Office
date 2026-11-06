package band.effective.office.backend.feature.board.core.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Meeting summary")
data class MeetingDTO(
    @Schema(description = "Meeting identifier", example = "1")
    val id: Long,

    @Schema(description = "Meeting title", example = "Board of directors meeting #1")
    val title: String,

    @Schema(description = "Date and time", example = "October 20, 2026, 11:00")
    val date: String,

    @Schema(description = "Number of participants", example = "10")
    val participantsCount: Int,

    @Schema(description = "Number of agenda questions", example = "5")
    val questionsCount: Int
)
