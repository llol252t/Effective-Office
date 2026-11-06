package band.effective.office.backend.feature.board.core.dto

import band.effective.office.backend.feature.board.core.domain.model.Meeting

/**
 * Mapper for converting board module domain models to DTOs.
 */
object BoardDtoMapper {

    /**
     * Converts a meeting domain model to a full DTO (with questions).
     */
    fun toMeetingDetailsDTO(meeting: Meeting): MeetingDetailsDTO {
        return MeetingDetailsDTO(
            id = meeting.id,
            title = meeting.title,
            date = meeting.date,
            questions = meeting.questions.mapIndexed { index, q ->
                QuestionDTO(
                    id = q.id,
                    number = index + 1,
                    text = q.text,
                    type = q.type,
                    options = q.options,
                    quorum = q.quorum,
                    rule = q.rule
                )
            }
        )
    }
}
