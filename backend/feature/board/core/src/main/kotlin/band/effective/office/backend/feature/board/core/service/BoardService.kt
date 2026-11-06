package band.effective.office.backend.feature.board.core.service

import band.effective.office.backend.feature.board.core.domain.model.Meeting
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * Сервис модуля совета директоров.
 * Данные хранятся в памяти и удаляются после завершения заседания.
 */
@Service
class BoardService {

    private val logger = LoggerFactory.getLogger(BoardService::class.java)

    /** Заседания, известные серверу (в памяти, без базы данных). */
    private val meetings = mutableListOf<Meeting>()

    /**
     * Возвращает список всех заседаний.
     */
    fun getMeetings(): List<Meeting> {
        logger.info("Returning {} meetings", meetings.size)
        return meetings.toList()
    }
}
