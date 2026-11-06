package band.effective.office.backend.feature.board.core.exception

/**
 * Base class of board module exceptions.
 * All module exceptions must extend this class.
 *
 * @property message detailed error description
 * @property errorCode error code (not an HTTP status)
 */
open class BoardException(
    override val message: String,
    val errorCode: Int
) : RuntimeException(message)

/**
 * Meeting with the given identifier was not found.
 */
class MeetingNotFoundException(
    message: String = "Meeting not found"
) : BoardException(message, BoardErrorCodes.MEETING_NOT_FOUND)

/**
 * Voting for this question is not open right now.
 */
class VotingNotOpenException(
    message: String = "Voting is not open for this question"
) : BoardException(message, BoardErrorCodes.VOTING_NOT_OPEN)

/**
 * The participant name is already claimed by another tablet.
 */
class ParticipantAlreadyClaimedException(
    message: String = "Participant name is already claimed"
) : BoardException(message, BoardErrorCodes.PARTICIPANT_ALREADY_CLAIMED)

/**
 * Invalid request parameters.
 */
class InvalidBoardRequestException(
    message: String = "Invalid board request"
) : BoardException(message, BoardErrorCodes.INVALID_REQUEST)
