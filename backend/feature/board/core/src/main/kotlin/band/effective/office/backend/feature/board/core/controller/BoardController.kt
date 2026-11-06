package band.effective.office.backend.feature.board.core.controller

import band.effective.office.backend.feature.board.core.dto.BoardDtoMapper
import band.effective.office.backend.feature.board.core.dto.MeetingDTO
import band.effective.office.backend.feature.board.core.service.BoardService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST-контроллер модуля совета директоров.
 * Эндпоинты для подготовки и проведения заседаний и голосований.
 */
@RestController
@RequestMapping("/v1/board")
@Tag(name = "Board", description = "Модуль совета директоров: заседания и голосования")
class BoardController(
    private val boardService: BoardService
) {

    /**
     * Проверка, что модуль подключён и сервер отвечает.
     */
    @GetMapping("/ping")
    @Operation(summary = "Ping", description = "Проверка доступности модуля")
    fun ping(): ResponseEntity<Map<String, String>> =
        ResponseEntity.ok(mapOf("status" to "ok"))

    /**
     * Возвращает список заседаний.
     */
    @GetMapping("/meetings")
    @Operation(
        summary = "Список заседаний",
        description = "Возвращает все заседания, известные серверу."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Список заседаний получен"),
        ]
    )
    fun getMeetings(): ResponseEntity<List<MeetingDTO>> {
        val meetings = boardService.getMeetings()
        val meetingDTOs = BoardDtoMapper.toMeetingDTOList(meetings)
        return ResponseEntity.ok(meetingDTOs)
    }
}
