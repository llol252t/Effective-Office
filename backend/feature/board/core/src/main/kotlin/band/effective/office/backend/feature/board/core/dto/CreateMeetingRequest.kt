package band.effective.office.backend.feature.board.core.dto

import band.effective.office.backend.feature.board.core.domain.model.DecisionRule
import band.effective.office.backend.feature.board.core.domain.model.VotingType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Запрос на создание заседания")
data class CreateMeetingRequest(
    @Schema(description = "Название заседания", example = "Заседание совета директоров № 1")
    val title: String,

    @Schema(description = "Дата и время", example = "20 октября 2026, 11:00")
    val date: String,

    @Schema(description = "Имена участников (в порядке списка для планшетов)")
    val participants: List<String>,

    @Schema(description = "Вопросы повестки")
    val questions: List<CreateQuestionRequest>
)

@Schema(description = "Вопрос при создании заседания")
data class CreateQuestionRequest(
    @Schema(description = "Формулировка вопроса", example = "Утвердить годовой бюджет")
    val text: String,

    @Schema(description = "Тип голосования", example = "FOR_AGAINST_ABSTAIN")
    val type: VotingType,

    @Schema(description = "Варианты ответов (только для SINGLE_CHOICE)")
    val options: List<String> = emptyList(),

    @Schema(description = "Минимальный кворум, участников", example = "7")
    val quorum: Int = 1,

    @Schema(description = "Правило принятия решения", example = "SIMPLE_MAJORITY")
    val rule: DecisionRule = DecisionRule.SIMPLE_MAJORITY
)
