package band.effective.office.backend.feature.board.core.dto

import band.effective.office.backend.feature.board.core.domain.model.Meeting

/**
 * Mapper for converting board module domain models to DTOs.
 */
object BoardDtoMapper {

    /**
     * Converts a meeting domain model to a short DTO (for lists).
     */
    fun toMeetingDTO(meeting: Meeting): MeetingDTO {
        return MeetingDTO(
            id = meeting.id,
            title = meeting.title,
            date = meeting.date,
            participantsCount = meeting.participantsCount,
            questionsCount = meeting.questions.size
        )
    }

    /**
     * Converts a list of meeting domain models to a list of short DTOs.
     */
    fun toMeetingDTOList(meetings: List<Meeting>): List<MeetingDTO> {
        return meetings.map { toMeetingDTO(it) }
    }

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
