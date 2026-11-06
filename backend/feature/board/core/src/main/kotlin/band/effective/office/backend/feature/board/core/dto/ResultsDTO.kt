package band.effective.office.backend.feature.board.core.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Прогресс голосования по вопросу (для админки)")
data class VoteProgressDTO(
    @Schema(description = "Сколько участников ответили", example = "8")
    val answered: Int,

    @Schema(description = "Всего участников", example = "10")
    val total: Int
)

@Schema(description = "Агрегированный результат по одному вопросу")
data class QuestionResultDTO(
    @Schema(description = "Идентификатор вопроса")
    val questionId: Long,

    @Schema(description = "Порядковый номер в повестке")
    val number: Int,

    @Schema(description = "Формулировка вопроса")
    val text: String,

    @Schema(description = "Голоса по вариантам: вариант -> количество")
    val counts: Map<String, Int>,

    @Schema(description = "Сколько участников ответили")
    val answered: Int,

    @Schema(description = "Кворум набран")
    val quorumReached: Boolean,

    @Schema(description = "Решение принято")
    val accepted: Boolean,

    @Schema(description = "Вариант-победитель (для SINGLE_CHOICE)")
    val winner: String? = null
)

@Schema(description = "Итоги заседания по всем вопросам")
data class MeetingResultsDTO(
    @Schema(description = "Идентификатор заседания")
    val meetingId: Long,

    @Schema(description = "Заседание завершено")
    val finished: Boolean,

    @Schema(description = "Результаты по вопросам")
    val results: List<QuestionResultDTO>
)
