package band.effective.office.backend.feature.board.core.domain.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

/**
 * Участник заседания.
 *
 * @property id уникальный идентификатор участника
 * @property name ФИО участника (как в списке у секретаря)
 */
data class Participant(
    @field:NotNull(message = "Participant id is required")
    val id: Long,

    @field:NotBlank(message = "Participant name is required")
    val name: String
)
