package band.effective.office.backend.feature.board.core.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Участник заседания (строка в списке на планшете)")
data class ParticipantDTO(
    @Schema(description = "Идентификатор участника", example = "1")
    val id: Long,

    @Schema(description = "ФИО участника", example = "Артём Сергеевич Волков")
    val name: String,

    @Schema(description = "Имя уже занято каким-то планшетом", example = "false")
    val claimed: Boolean
)

@Schema(description = "Запрос на авторизацию: планшет занимает имя участника")
data class ClaimParticipantRequest(
    @Schema(description = "Идентификатор выбранного участника", example = "1")
    val participantId: Long
)
