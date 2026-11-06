package band.effective.office.backend.feature.board.core.dto

import band.effective.office.backend.feature.board.core.domain.model.DecisionRule
import band.effective.office.backend.feature.board.core.domain.model.VotingType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Полная информация о заседании (для планшета и админки)")
data class MeetingDetailsDTO(
    @Schema(description = "Идентификатор заседания", example = "1")
    val id: Long,

    @Schema(description = "Название заседания")
    val title: String,

    @Schema(description = "Дата и время")
    val date: String,

    @Schema(description = "Вопросы повестки")
    val questions: List<QuestionDTO>
)

@Schema(description = "Вопрос повестки")
data class QuestionDTO(
    @Schema(description = "Идентификатор вопроса", example = "1")
    val id: Long,

    @Schema(description = "Порядковый номер в повестке", example = "1")
    val number: Int,

    @Schema(description = "Формулировка вопроса")
    val text: String,

    @Schema(description = "Тип голосования")
    val type: VotingType,

    @Schema(description = "Варианты ответов (для SINGLE_CHOICE)")
    val options: List<String>,

    @Schema(description = "Минимальный кворум")
    val quorum: Int,

    @Schema(description = "Правило принятия решения")
    val rule: DecisionRule
)
