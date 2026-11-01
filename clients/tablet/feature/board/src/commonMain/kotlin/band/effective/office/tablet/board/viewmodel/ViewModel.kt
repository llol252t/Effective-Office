package band.effective.office.tablet.board.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import band.effective.office.tablet.board.model.Meeting
class WaitingViewModel : ViewModel() {
    private val mockMeeting = Meeting(
        id = 1,
        title = "Заседание совета директоров № 1",
        date = "20 октября 2026, 11:00",
        participantName = "Артём Сергеевич Волков",
        questions = listOf(
            "Утвердить повестку и регламент заседания",
            "Утвердить годовой бюджет на следующий период",
            "Одобрить крупную сделку",
            "Выбрать корпоративного секретаря",
            "Определить дату следующего заседания"
        )
    )
    private val _meeting = MutableStateFlow(mockMeeting)
    val meeting: StateFlow<Meeting> = _meeting.asStateFlow()
}