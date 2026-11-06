package band.effective.office.backend.feature.board.core.exception

import band.effective.office.backend.core.data.ErrorDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * Обработчик исключений модуля board.
 */
@RestControllerAdvice
class BoardExceptionHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(MeetingNotFoundException::class)
    fun handleMeetingNotFound(ex: MeetingNotFoundException): ResponseEntity<ErrorDto> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorDto(message = ex.message, code = ex.errorCode)
        )

    @ExceptionHandler(VotingNotOpenException::class)
    fun handleVotingNotOpen(ex: VotingNotOpenException): ResponseEntity<ErrorDto> =
        ResponseEntity.status(HttpStatus.CONFLICT).body(
            ErrorDto(message = ex.message, code = ex.errorCode)
        )

    @ExceptionHandler(ParticipantAlreadyClaimedException::class)
    fun handleParticipantClaimed(ex: ParticipantAlreadyClaimedException): ResponseEntity<ErrorDto> =
        ResponseEntity.status(HttpStatus.CONFLICT).body(
            ErrorDto(message = ex.message, code = ex.errorCode)
        )

    @ExceptionHandler(InvalidBoardRequestException::class)
    fun handleInvalidRequest(ex: InvalidBoardRequestException): ResponseEntity<ErrorDto> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorDto(message = ex.message, code = ex.errorCode)
        )

    /** Общий обработчик BoardException. */
    @ExceptionHandler(BoardException::class)
    fun handleBoardException(ex: BoardException): ResponseEntity<ErrorDto> {
        logger.error("Board exception: {}", ex.message)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ErrorDto(message = ex.message, code = ex.errorCode)
        )
    }
}
