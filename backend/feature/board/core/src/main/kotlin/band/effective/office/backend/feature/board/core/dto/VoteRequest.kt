package band.effective.office.backend.feature.board.core.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Голос участника")
data class VoteRequest(
    @Schema(description = "Идентификатор участника (планшет знает свой после авторизации)", example = "3")
    val participantId: Long,

    @Schema(description = "Выбранный ответ", example = "За")
    val answer: String
)
