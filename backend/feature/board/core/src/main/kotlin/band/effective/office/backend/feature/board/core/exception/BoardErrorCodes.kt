package band.effective.office.backend.feature.board.core.exception

/**
 * Коды ошибок модуля board.
 * Константы определяют коды ошибок, возвращаемые модулем совета директоров.
 */
object BoardErrorCodes {
    // Ошибки состояния заседания (4xx)
    const val MEETING_NOT_FOUND = 401
    const val PARTICIPANT_ALREADY_CLAIMED = 402

    // Ошибки состояния голосования (5xx)
    const val VOTING_NOT_OPEN = 501

    // Ошибки валидации (6xx)
    const val INVALID_REQUEST = 601
}
