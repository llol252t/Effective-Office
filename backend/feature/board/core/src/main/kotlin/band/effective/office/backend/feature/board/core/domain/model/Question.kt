package band.effective.office.backend.feature.board.core.domain.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

/**
 * Type of voting for a question.
 */
enum class VotingType {
    /** For / against / abstain */
    FOR_AGAINST_ABSTAIN,

    /** Single choice from a list of options */
    SINGLE_CHOICE
}

/**
 * Decision rule for a question.
 */
enum class DecisionRule {
    /** Simple majority of votes */
    SIMPLE_MAJORITY,

    /** At least two thirds of votes */
    TWO_THIRDS,

    /** All participants voted "for" */
    UNANIMOUS
}

/**
 * Domain model representing an agenda question.
 *
 * @property id unique question identifier
 * @property text question wording
 * @property type voting type
 * @property options answer options (for SINGLE_CHOICE, empty otherwise)
 * @property quorum minimum number of voters for the vote to be valid
 * @property rule decision rule
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
