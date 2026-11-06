package band.effective.office.backend.feature.board.core.dto

import band.effective.office.backend.feature.board.core.domain.model.Meeting

/**
 * Маппер для конвертации доменных моделей модуля board в DTO.
 */
object BoardDtoMapper {

    /**
     * Конвертирует доменную модель заседания в краткий DTO (для списка).
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
     * Конвертирует список доменных моделей заседаний в список кратких DTO.
     */
    fun toMeetingDTOList(meetings: List<Meeting>): List<MeetingDTO> {
        return meetings.map { toMeetingDTO(it) }
    }

    /**
     * Конвертирует доменную модель заседания в полный DTO (с вопросами).
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
