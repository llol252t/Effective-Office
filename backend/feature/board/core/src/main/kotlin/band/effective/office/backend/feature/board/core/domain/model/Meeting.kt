package band.effective.office.backend.feature.board.core.domain.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

/**
 * Доменная модель заседания совета директоров.
 *
 * @property id уникальный идентификатор заседания
 * @property title название заседания
 * @property date дата и время проведения (строкой, как в макетах)
 * @property participantsCount ожидаемое число участников
 * @property questions вопросы повестки
 */
data class Meeting(
    @field:NotNull(message = "Meeting id is required")
    val id: Long,

    @field:NotBlank(message = "Meeting title is required")
    val title: String,

    @field:NotBlank(message = "Meeting date is required")
    val date: String,

    val participantsCount: Int,

    val questions: List<Question> = emptyList()
)
