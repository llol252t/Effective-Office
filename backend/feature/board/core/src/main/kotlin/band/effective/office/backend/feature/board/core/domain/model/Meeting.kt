package band.effective.office.backend.feature.board.core.domain.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

/**
 * Domain model representing a board of directors meeting.
 *
 * @property id unique meeting identifier
 * @property title meeting title
 * @property date date and time (string, as shown in the mockups)
 * @property participantsCount expected number of participants
 * @property questions agenda questions
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
