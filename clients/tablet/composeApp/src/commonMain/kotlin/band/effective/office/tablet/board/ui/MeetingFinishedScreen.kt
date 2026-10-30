package band.effective.office.tablet.board.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MeetingDecision(
    val text: String,
    val isAccepted: Boolean
)

private val SampleDecisions = listOf(
    MeetingDecision("Утвердить повестку и регламент заседания", true),
    MeetingDecision("Утвердить годовой бюджет на следующий период", true),
    MeetingDecision("Одобрить крупную сделку", false),
    MeetingDecision("Выбрать корпоративного секретаря", true),
    MeetingDecision("Определить дату следующего заседания", true)
)

@Composable
fun MeetingFinishedScreen(
    decisions: List<MeetingDecision> = SampleDecisions
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BoardColors.DarkBackground)
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Совет директоров",
                color = BoardColors.WhiteText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier
                    .background(BoardColors.CardBackground, RoundedCornerShape(50))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(BoardColors.GreenText, RoundedCornerShape(50))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Подключено к серверу", color = BoardColors.WhiteText, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ThanksCard(modifier = Modifier.weight(0.4f))
            DecisionsCard(decisions = decisions, modifier = Modifier.weight(0.6f))
        }
    }
}

@Composable
private fun ThanksCard(modifier: Modifier = Modifier) {
    FinishedCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .background(BoardColors.GreenText, RoundedCornerShape(50)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "✓",
                    color = BoardColors.DarkContent,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "Все вопросы рассмотрены",
                color = BoardColors.WhiteText,
                fontSize = 36.sp,
                lineHeight = 42.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Спасибо за участие. Секретарь оформит протокол и завершит заседание.",
                color = BoardColors.GrayText,
                fontSize = 20.sp,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(BoardColors.BorderGray)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "После завершения ваши ответы будут удалены. Верните планшет секретарю.",
                color = BoardColors.GrayText,
                fontSize = 16.sp,
                lineHeight = 22.sp
            )
        }
    }
}

@Composable
private fun DecisionsCard(decisions: List<MeetingDecision>, modifier: Modifier = Modifier) {
    FinishedCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp)
        ) {
            Text("РЕШЕНИЯ ПО ВОПРОСАМ", color = BoardColors.GrayText, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                decisions.forEachIndexed { index, decision ->
                    DecisionRow(number = index + 1, decision = decision)
                }
            }
        }
    }
}

@Composable
private fun DecisionRow(number: Int, decision: MeetingDecision) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(BoardColors.OptionBackground, RoundedCornerShape(16.dp))
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            number.toString(),
            color = BoardColors.GrayText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(32.dp)
        )
        Text(
            decision.text,
            color = BoardColors.WhiteText,
            fontSize = 20.sp,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        DecisionBadge(isAccepted = decision.isAccepted)
    }
}

@Composable
private fun DecisionBadge(isAccepted: Boolean) {
    Box(
        modifier = Modifier
            .background(
                if (isAccepted) BoardColors.VoteGreen else BoardColors.VoteRed,
                RoundedCornerShape(50)
            )
            .padding(horizontal = 16.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            if (isAccepted) "Принято" else "Не принято",
            color = if (isAccepted) BoardColors.DarkContent else BoardColors.WhiteText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun FinishedCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(BoardColors.CardBackground, RoundedCornerShape(24.dp))
    ) {
        content()
    }
}
