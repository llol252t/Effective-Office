package band.effective.office.backend.feature.board.core.domain.model

import jakarta.validation.constraints.NotNull

/**
 * Доменная модель голоса участника.
 * Хранится только до завершения заседания, затем удаляется.
 *
 * @property questionId идентификатор вопроса
 * @property participantId идентификатор участника (планшета)
 * @property answer выбранный ответ (вариант)
 */
data class Vote(
    @field:NotNull(message = "Question id is required")
    val questionId: Long,

    @field:NotNull(message = "Participant id is required")
    val participantId: Long,

    @field:NotNull(message = "Answer is required")
    val answer: String
)
