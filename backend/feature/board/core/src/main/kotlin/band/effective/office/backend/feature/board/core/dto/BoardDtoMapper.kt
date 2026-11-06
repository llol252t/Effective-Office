package band.effective.office.backend.feature.board.core.dto

import band.effective.office.backend.feature.board.core.domain.model.Meeting

/**
 * Маппер для конвертации доменных моделей модуля board в DTO.
 */
object BoardDtoMapper {

    /**
     * Конвертирует доменную модель заседания в DTO.
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
     * Конвертирует список доменных моделей заседаний в список DTO.
     */
    fun toMeetingDTOList(meetings: List<Meeting>): List<MeetingDTO> {
        return meetings.map { toMeetingDTO(it) }
    }
}
