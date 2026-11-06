package band.effective.office.backend.feature.board.core.domain.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

/**
 * Тип голосования по вопросу.
 */
enum class VotingType {
    /** За / против / воздержался */
    FOR_AGAINST_ABSTAIN,

    /** Выбор одного варианта из списка */
    SINGLE_CHOICE
}

/**
 * Правило принятия решения по вопросу.
 */
enum class DecisionRule {
    /** Простое большинство голосов */
    SIMPLE_MAJORITY,

    /** Не менее двух третей голосов */
    TWO_THIRDS,

    /** Все участники проголосовали «за» */
    UNANIMOUS
}

/**
 * Доменная модель вопроса повестки.
 *
 * @property id уникальный идентификатор вопроса
 * @property text формулировка вопроса
 * @property type тип голосования
 * @property options варианты ответов (для SINGLE_CHOICE, иначе пусто)
 * @property quorum минимальное число проголосовавших для действительности
 * @property rule правило принятия решения
 */
data class Question(
    @field:NotNull(message = "Question id is required")
    val id: Long,

    @field:NotBlank(message = "Question text is required")
    val text: String,

    @field:NotNull(message = "Voting type is required")
    val type: VotingType,

    val options: List<String> = emptyList(),

    val quorum: Int = 1,

    @field:NotNull(message = "Decision rule is required")
    val rule: DecisionRule = DecisionRule.SIMPLE_MAJORITY
)
