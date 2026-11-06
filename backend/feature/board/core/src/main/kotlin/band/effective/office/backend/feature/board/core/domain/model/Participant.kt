package band.effective.office.backend.feature.board.core.domain.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

/**
 * Meeting participant.
 *
 * @property id unique participant identifier
 * @property name participant full name (as in the secretary's list)
 */
data class Participant(
    @field:NotNull(message = "Participant id is required")
    val id: Long,

    @field:NotBlank(message = "Participant name is required")
    val name: String
)
