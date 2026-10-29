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
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun WaitingScreen(
    viewModel: WaitingViewModel = viewModel()
) {
    val meeting by viewModel.meeting.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(BoardColors.DarkBackground)
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Column(
            modifier = Modifier.weight(0.6f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CardContainer {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("ВАШЕ ЗАСЕДАНИЕ", color = BoardColors.GrayText, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(meeting.title, color = BoardColors.WhiteText, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(meeting.date, color = BoardColors.GrayText, fontSize = 16.sp)
                }
            }

            CardContainer(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("ПОВЕСТКА · ${meeting.questions.size} ВОПРОСОВ", color = BoardColors.GrayText, fontSize = 12.sp)
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
                    .border(1.dp, BoardColors.GreenText, RoundedCornerShape(16.dp))
                    .padding(24.dp)
            ) {
                Column {
                    Text("● Голосование ещё не началось", color = BoardColors.GreenText, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Ожидаем начала голосования.", color = BoardColors.WhiteText, fontSize = 16.sp)
                }
            }

            CardContainer {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("УЧАСТНИК", color = BoardColors.GrayText, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(meeting.participantName, color = BoardColors.WhiteText, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Планшет подготовлен для вас", color = BoardColors.GrayText, fontSize = 14.sp)
                }
            }

            CardContainer {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("КОНФИДЕНЦИАЛЬНОСТЬ", color = BoardColors.GrayText, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Данные остаются в сети компании. Ваш выбор не раскрывается другим участникам и не сохраняется после заседания.",
                        color = BoardColors.GrayText, fontSize = 14.sp
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
            .background(BoardColors.CardBackground, RoundedCornerShape(16.dp))
    ) {
        content()
    }
}

@Composable
fun QuestionRow(number: Int, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(BoardColors.OptionBackground, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(BoardColors.SelectedBackground, RoundedCornerShape(50)),
            contentAlignment = Alignment.Center
        ) {
            Text(number.toString(), color = BoardColors.WhiteText, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, color = BoardColors.WhiteText, fontSize = 16.sp)
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 600)
@Composable
fun WaitingScreenPreview() {
    WaitingScreen()
}