package band.effective.office.tablet.board.model

data class Meeting(
    val id: Int,
    val title: String,
    val date: String,
    val participantName: String,
    val questions: List<String>
)