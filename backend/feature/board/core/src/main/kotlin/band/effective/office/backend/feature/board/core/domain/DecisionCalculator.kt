package band.effective.office.backend.feature.board.core.domain

import band.effective.office.backend.feature.board.core.domain.model.DecisionRule
import band.effective.office.backend.feature.board.core.domain.model.Question
import band.effective.office.backend.feature.board.core.domain.model.VotingType

/** Standard options for the "for/against/abstain" voting type. */
const val ANSWER_FOR = "За"
const val ANSWER_AGAINST = "Против"
const val ANSWER_ABSTAIN = "Воздержался"

/**
 * Pure vote-counting logic.
 * Does not depend on Spring — easy to cover with unit tests.
 */
object DecisionCalculator {

    /**
     * Counts votes for each answer option.
     */
    fun countVotes(question: Question, answers: List<String>): Map<String, Int> {
        val variants = when (question.type) {
            VotingType.FOR_AGAINST_ABSTAIN -> listOf(ANSWER_FOR, ANSWER_AGAINST, ANSWER_ABSTAIN)
            VotingType.SINGLE_CHOICE -> question.options
        }
        return variants.associateWith { variant -> answers.count { it == variant } }
    }

    /**
     * Whether quorum is reached: the number of voters is not less than required.
     */
    fun hasQuorum(question: Question, votedCount: Int): Boolean = votedCount >= question.quorum

    /**
     * Whether the decision on the question is accepted.
     * For FOR_AGAINST_ABSTAIN — according to the question's rule.
     * For SINGLE_CHOICE — the question is "accepted" if quorum is reached (see [winner]).
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
            // "two thirds" of for/against votes: for * 3 >= 2 * (for + against)
            DecisionRule.TWO_THIRDS -> forCount > 0 && forCount * 3 >= 2 * (forCount + againstCount)
            DecisionRule.UNANIMOUS -> forCount > 0 && againstCount == 0 && abstainCount == 0
        }
    }

    /**
     * Winning option for "single choice" voting (simple majority).
     * Returns null if quorum is not reached.
     */
    fun winner(question: Question, answers: List<String>): String? {
        if (question.type != VotingType.SINGLE_CHOICE) return null
        if (!hasQuorum(question, answers.size)) return null
        return countVotes(question, answers).maxByOrNull { it.value }?.key
    }
}
