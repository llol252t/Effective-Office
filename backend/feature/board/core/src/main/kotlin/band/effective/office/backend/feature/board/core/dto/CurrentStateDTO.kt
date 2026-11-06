package band.effective.office.backend.feature.board.core.dto

import band.effective.office.backend.feature.board.core.domain.model.MeetingStatus
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Текущее состояние заседания для планшета (polling)")
data class CurrentStateDTO(
    @Schema(description = "Состояние заседания", example = "VOTING_OPEN")
    val status: MeetingStatus,

    @Schema(description = "Текущий открытый вопрос (если статус VOTING_OPEN)")
    val currentQuestion: QuestionDTO? = null
)
