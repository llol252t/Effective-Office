package band.effective.office.backend.feature.board.core.domain

import band.effective.office.backend.feature.board.core.domain.model.DecisionRule
import band.effective.office.backend.feature.board.core.domain.model.Question
import band.effective.office.backend.feature.board.core.domain.model.VotingType

/** Стандартные варианты для голосования «за/против/воздержался». */
const val ANSWER_FOR = "За"
const val ANSWER_AGAINST = "Против"
const val ANSWER_ABSTAIN = "Воздержался"

/**
 * Чистая логика подсчёта результатов голосования.
 * Не зависит от Spring — удобно покрывать юнит-тестами.
 */
object DecisionCalculator {

    /**
     * Подсчёт голосов по каждому варианту ответа.
     */
    fun countVotes(question: Question, answers: List<String>): Map<String, Int> {
        val variants = when (question.type) {
            VotingType.FOR_AGAINST_ABSTAIN -> listOf(ANSWER_FOR, ANSWER_AGAINST, ANSWER_ABSTAIN)
            VotingType.SINGLE_CHOICE -> question.options
        }
        return variants.associateWith { variant -> answers.count { it == variant } }
    }

    /**
     * Набран ли кворум: проголосовавших не меньше, чем требуется.
     */
    fun hasQuorum(question: Question, votedCount: Int): Boolean = votedCount >= question.quorum

    /**
     * Принято ли решение по вопросу.
     * Для FOR_AGAINST_ABSTAIN — по правилу из вопроса.
     * Для SINGLE_CHOICE — вопрос «принят», если есть кворум (победитель см. [winner]).
     */
    fun isAccepted(question: Question, answers: List<String>): Boolean {
        if (!hasQuorum(question, answers.size)) return false
        if (question.type == VotingType.SINGLE_CHOICE) return true

        val counts = countVotes(question, answers)
        val forCount = counts[ANSWER_FOR] ?: 0
        val againstCount = counts[ANSWER_AGAINST] ?: 0
        val abstainCount = counts[ANSWER_ABSTAIN] ?: 0

        return when (question.rule) {
            DecisionRule.SIMPLE_MAJORITY -> forCount > againstCount
            // «две трети» от проголосовавших «за»/«против»: for * 3 >= 2 * (for + against)
            DecisionRule.TWO_THIRDS -> forCount > 0 && forCount * 3 >= 2 * (forCount + againstCount)
            DecisionRule.UNANIMOUS -> forCount > 0 && againstCount == 0 && abstainCount == 0
        }
    }

    /**
     * Вариант-победитель для голосования «выбор из списка» (простое большинство).
     * Возвращает null, если кворум не набран.
     */
    fun winner(question: Question, answers: List<String>): String? {
        if (question.type != VotingType.SINGLE_CHOICE) return null
        if (!hasQuorum(question, answers.size)) return null
        return countVotes(question, answers).maxByOrNull { it.value }?.key
    }
}
