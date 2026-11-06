package band.effective.office.backend.feature.board.core.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Информация о заседании")
data class MeetingDTO(
    @Schema(description = "Идентификатор заседания", example = "1")
    val id: Long,

    @Schema(description = "Название заседания", example = "Заседание совета директоров № 1")
    val title: String,

    @Schema(description = "Дата и время", example = "20 октября 2026, 11:00")
    val date: String,

    @Schema(description = "Число участников", example = "10")
    val participantsCount: Int,

    @Schema(description = "Число вопросов повестки", example = "5")
    val questionsCount: Int
)
