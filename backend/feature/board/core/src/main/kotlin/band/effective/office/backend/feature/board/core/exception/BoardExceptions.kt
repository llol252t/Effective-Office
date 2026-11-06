package band.effective.office.backend.feature.board.core.exception

/**
 * Базовый класс исключений модуля board.
 * Все исключения модуля должны наследоваться от этого класса.
 *
 * @property message подробное описание ошибки
 * @property errorCode код ошибки (не HTTP-статус)
 */
open class BoardException(
    override val message: String,
    val errorCode: Int
) : RuntimeException(message)

/**
 * Заседание с указанным идентификатором не найдено.
 */
class MeetingNotFoundException(
    message: String = "Meeting not found"
) : BoardException(message, BoardErrorCodes.MEETING_NOT_FOUND)

/**
 * Голосование по вопросу сейчас не открыто.
 */
class VotingNotOpenException(
    message: String = "Voting is not open for this question"
) : BoardException(message, BoardErrorCodes.VOTING_NOT_OPEN)

/**
 * Некорректные входные параметры запроса.
 */
class InvalidBoardRequestException(
    message: String = "Invalid board request"
) : BoardException(message, BoardErrorCodes.INVALID_REQUEST)
