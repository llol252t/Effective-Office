package band.effective.office.backend.feature.board.core.exception

/**
 * Error codes of the board module.
 * These constants define the error codes returned by the board of directors module.
 */
object BoardErrorCodes {
    // Meeting state errors (4xx)
    const val MEETING_NOT_FOUND = 401
    const val PARTICIPANT_ALREADY_CLAIMED = 402

    // Voting state errors (5xx)
    const val VOTING_NOT_OPEN = 501

    // Validation errors (6xx)
    const val INVALID_REQUEST = 601
}
