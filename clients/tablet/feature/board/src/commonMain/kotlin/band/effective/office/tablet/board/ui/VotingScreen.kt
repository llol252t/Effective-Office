package band.effective.office.tablet.board.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val LOG_TAG = "VotingScreen"

private enum class VoteOption(
    val title: String,
    val symbol: String,
    val background: Color,
    val contentColor: Color
) {
    FOR("За", "✓", BoardColors.VoteGreen, BoardColors.DarkContent),
    AGAINST("Против", "✕", BoardColors.VoteRed, BoardColors.WhiteText),
    ABSTAIN("Воздержался", "—", BoardColors.VoteGray, BoardColors.DarkContent)
}

@Composable
fun VotingScreen(
    questionNumber: Int = 2,
    totalQuestions: Int = 5,
    questionText: String = "Утвердить годовой бюджет на следующий период",
    onConfirm: (String) -> Unit = { println("[$LOG_TAG] Выбрано: $it") }
) {
    var selected by remember { mutableStateOf<VoteOption?>(null) }

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
            Text("Совет директоров", color = BoardColors.WhiteText, fontSize = 16.sp, fontWeight = FontWeight.Bold)

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

        Text(
            "Вопрос $questionNumber из $totalQuestions",
            color = BoardColors.GreenText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            questionText,
            color = BoardColors.WhiteText,
            fontSize = 36.sp,
            lineHeight = 44.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            VoteOption.values().forEach { option ->
                VoteButton(
                    option = option,
                    isSelected = option == selected,
                    modifier = Modifier.weight(1f),
                    onClick = { selected = option }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Итог появится, когда ответят все участники", color = BoardColors.GrayText, fontSize = 14.sp)
            ConfirmButton(
                enabled = selected != null,
                onClick = { selected?.let { onConfirm(it.title) } }
            )
        }
    }
}

@Composable
private fun VoteButton(
    option: VoteOption,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(24.dp)

    Box(
        modifier = modifier
            .height(160.dp)
            .clip(shape)
            .background(option.background)
            .then(if (isSelected) Modifier.border(4.dp, BoardColors.WhiteText, shape) else Modifier)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(option.symbol, color = option.contentColor, fontSize = 40.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(option.title, color = option.contentColor, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ConfirmButton(enabled: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(BoardColors.ConfirmOrange.copy(alpha = if (enabled) 1f else 0.4f))
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 40.dp, vertical = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Подтвердить выбор", color = BoardColors.DarkContent, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(
    showBackground = true,
    widthDp = 800,
    heightDp = 600,
    name = "Экран голосования За/Против"
)
@Composable
fun VotingScreenPreview() {
    VotingScreen(
        questionNumber = 2,
        totalQuestions = 5,
        questionText = "Утвердить годовой бюджет на следующий период"
    )
}