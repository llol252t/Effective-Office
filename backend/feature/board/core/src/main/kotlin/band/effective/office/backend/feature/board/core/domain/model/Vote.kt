package band.effective.office.backend.feature.board.core.domain.model

import jakarta.validation.constraints.NotNull

/**
 * Domain model representing a participant's vote.
 * Stored only until the meeting is finished, then deleted.
 *
 * @property questionId question identifier
 * @property participantId participant (tablet) identifier
 * @property answer chosen answer (option)
 */
data class Vote(
    @field:NotNull(message = "Question id is required")
    val questionId: Long,

    @field:NotNull(message = "Participant id is required")
    val participantId: Long,

    @field:NotNull(message = "Answer is required")
    val answer: String
)
