package band.effective.office.backend.feature.board.core.controller

import band.effective.office.backend.feature.board.core.dto.BoardDtoMapper
import band.effective.office.backend.feature.board.core.dto.ClaimParticipantRequest
import band.effective.office.backend.feature.board.core.dto.CreateMeetingRequest
import band.effective.office.backend.feature.board.core.dto.CurrentStateDTO
import band.effective.office.backend.feature.board.core.dto.MeetingDTO
import band.effective.office.backend.feature.board.core.dto.MeetingDetailsDTO
import band.effective.office.backend.feature.board.core.dto.MeetingResultsDTO
import band.effective.office.backend.feature.board.core.dto.ParticipantDTO
import band.effective.office.backend.feature.board.core.dto.VoteProgressDTO
import band.effective.office.backend.feature.board.core.dto.VoteRequest
import band.effective.office.backend.feature.board.core.service.BoardService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST-контроллер модуля совета директоров.
 * Подготовка заседания, авторизация участников, голосование, итоги.
 */
@RestController
@RequestMapping("/v1/board")
@Tag(name = "Board", description = "Модуль совета директоров: заседания и голосования")
class BoardController(
    private val boardService: BoardService
) {

    /** Проверка, что модуль подключён и сервер отвечает. */
    @GetMapping("/ping")
    @Operation(summary = "Ping", description = "Проверка доступности модуля")
    fun ping(): ResponseEntity<Map<String, String>> =
        ResponseEntity.ok(mapOf("status" to "ok"))

    // ---------- Подготовка (секретарь) ----------

    @PostMapping("/meetings")
    @Operation(summary = "Создать заседание", description = "Название, дата, участники и вопросы повестки")
    fun createMeeting(@RequestBody request: CreateMeetingRequest): ResponseEntity<MeetingDetailsDTO> {
        val meeting = boardService.createMeeting(request)
        return ResponseEntity.ok(BoardDtoMapper.toMeetingDetailsDTO(meeting))
    }

    @GetMapping("/meetings")
    @Operation(summary = "Список заседаний")
    fun getMeetings(): ResponseEntity<List<MeetingDTO>> =
        ResponseEntity.ok(BoardDtoMapper.toMeetingDTOList(boardService.getMeetings()))

    @GetMapping("/meetings/{meetingId}")
    @Operation(summary = "Заседание по id", description = "Полная информация: вопросы, кворум, правила")
    fun getMeeting(@PathVariable meetingId: Long): ResponseEntity<MeetingDetailsDTO> =
        ResponseEntity.ok(BoardDtoMapper.toMeetingDetailsDTO(boardService.getMeeting(meetingId)))

    // ---------- Авторизация участников (планшеты) ----------

    @GetMapping("/meetings/{meetingId}/participants")
    @Operation(summary = "Список участников", description = "Планшет получает имена для выбора; занятые помечены claimed=true")
    fun getParticipants(@PathVariable meetingId: Long): ResponseEntity<List<ParticipantDTO>> =
        ResponseEntity.ok(boardService.getParticipants(meetingId))

    @PostMapping("/meetings/{meetingId}/participants/claim")
    @Operation(summary = "Занять имя", description = "Планшет авторизуется под именем участника. Занятое имя — ошибка 409")
    fun claimParticipant(
        @PathVariable meetingId: Long,
        @RequestBody request: ClaimParticipantRequest
    ): ResponseEntity<ParticipantDTO> =
        ResponseEntity.ok(boardService.claimParticipant(meetingId, request))

    // ---------- Проведение голосования ----------

    @PostMapping("/meetings/{meetingId}/questions/{questionNumber}/open")
    @Operation(summary = "Открыть голосование", description = "Секретарь открывает голосование по вопросу (номер в повестке)")
    fun openVoting(
        @PathVariable meetingId: Long,
        @PathVariable questionNumber: Int
    ): ResponseEntity<Map<String, String>> {
        boardService.openVoting(meetingId, questionNumber)
        return ResponseEntity.ok(mapOf("status" to "open"))
    }

    @GetMapping("/meetings/{meetingId}/current")
    @Operation(summary = "Текущее состояние", description = "Планшет опрашивает сервер: ожидание / идёт голосование / итог / конец")
    fun getCurrentState(@PathVariable meetingId: Long): ResponseEntity<CurrentStateDTO> =
        ResponseEntity.ok(boardService.getCurrentState(meetingId))

    @PostMapping("/meetings/{meetingId}/questions/{questionNumber}/vote")
    @Operation(summary = "Проголосовать", description = "Участник отправляет ответ по открытому вопросу. Повторный голос — ошибка")
    fun vote(
        @PathVariable meetingId: Long,
        @PathVariable questionNumber: Int,
        @RequestBody request: VoteRequest
    ): ResponseEntity<Map<String, String>> {
        boardService.vote(meetingId, questionNumber, request)
        return ResponseEntity.ok(mapOf("status" to "accepted"))
    }

    @GetMapping("/meetings/{meetingId}/questions/{questionNumber}/progress")
    @Operation(summary = "Прогресс голосования", description = "Сколько участников ответили из общего числа (для админки)")
    fun getProgress(
        @PathVariable meetingId: Long,
        @PathVariable questionNumber: Int
    ): ResponseEntity<VoteProgressDTO> =
        ResponseEntity.ok(boardService.getProgress(meetingId, questionNumber))

    @PostMapping("/meetings/{meetingId}/questions/{questionNumber}/close")
    @Operation(summary = "Закрыть голосование", description = "Секретарь закрывает голосование, в т.ч. не дожидаясь остальных")
    fun closeVoting(
        @PathVariable meetingId: Long,
        @PathVariable questionNumber: Int
    ): ResponseEntity<Map<String, String>> {
        boardService.closeVoting(meetingId, questionNumber)
        return ResponseEntity.ok(mapOf("status" to "closed"))
    }

    // ---------- Итоги ----------

    @GetMapping("/meetings/{meetingId}/results")
    @Operation(summary = "Итоги заседания", description = "Агрегированные результаты по всем вопросам, без индивидуальных голосов")
    fun getResults(@PathVariable meetingId: Long): ResponseEntity<MeetingResultsDTO> =
        ResponseEntity.ok(boardService.getResults(meetingId))

    @PostMapping("/meetings/{meetingId}/finish")
    @Operation(summary = "Завершить заседание", description = "Удаляет индивидуальные голоса и привязки планшетов; возвращает финальные итоги")
    fun finishMeeting(@PathVariable meetingId: Long): ResponseEntity<MeetingResultsDTO> =
        ResponseEntity.ok(boardService.finishMeeting(meetingId))
}
