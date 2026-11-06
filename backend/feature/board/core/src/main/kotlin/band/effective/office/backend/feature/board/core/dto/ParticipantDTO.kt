package band.effective.office.backend.feature.board.core.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Meeting participant (a row in the list on the tablet)")
data class ParticipantDTO(
    @Schema(description = "Participant identifier", example = "1")
    val id: Long,

    @Schema(description = "Participant full name", example = "Artem Sergeevich Volkov")
    val name: String,

    @Schema(description = "Name is already claimed by some tablet", example = "false")
    val claimed: Boolean
)

@Schema(description = "Authorization request: the tablet claims a participant name")
data class ClaimParticipantRequest(
    @Schema(description = "Identifier of the selected participant", example = "1")
    val participantId: Long
)
