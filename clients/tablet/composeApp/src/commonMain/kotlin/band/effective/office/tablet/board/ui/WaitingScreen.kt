package band.effective.office.tablet.board.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import band.effective.office.tablet.board.viewmodel.WaitingViewModel

val DarkBackground = Color(0xFF1E1E1E)
val CardBackground = Color(0xFF2C2C2C)
val GreenText = Color(0xFF4CAF50)
val GrayText = Color(0xFF9E9E9E)

@Composable
fun WaitingScreen(
    viewModel: WaitingViewModel = viewModel()
) {
    val meeting by viewModel.meeting.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Column(
            modifier = Modifier.weight(0.6f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CardContainer {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("ВАШЕ ЗАСЕДАНИЕ", color = GrayText, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(meeting.title, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(meeting.date, color = GrayText, fontSize = 16.sp)
                }
            }

            CardContainer(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("ПОВЕСТКА · ${meeting.questions.size} ВОПРОСОВ", color = GrayText, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(16.dp))


                    meeting.questions.forEachIndexed { index, question ->
                        QuestionRow(number = index + 1, text = question)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }

        Column(
            modifier = Modifier.weight(0.4f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, GreenText, RoundedCornerShape(16.dp))
                    .padding(24.dp)
            ) {
                Column {
                    Text("● Голосование ещё не началось", color = GreenText, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Ожидаем начала голосования.", color = Color.White, fontSize = 16.sp)
                }
            }

            CardContainer {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("УЧАСТНИК", color = GrayText, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(meeting.participantName, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Планшет подготовлен для вас", color = GrayText, fontSize = 14.sp)
                }
            }

            CardContainer {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("КОНФИДЕНЦИАЛЬНОСТЬ", color = GrayText, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Данные остаются в сети компании. Ваш выбор не раскрывается другим участникам и не сохраняется после заседания.",
                        color = GrayText, fontSize = 14.sp
                    )
                }
            }
        }
    }
}


@Composable
fun CardContainer(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(CardBackground, RoundedCornerShape(16.dp))
    ) {
        content()
    }
}

@Composable
fun QuestionRow(number: Int, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF383838), RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(Color(0xFF4A4A4A), RoundedCornerShape(50)),
            contentAlignment = Alignment.Center
        ) {
            Text(number.toString(), color = Color.White, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, color = Color.White, fontSize = 16.sp)
    }
}
