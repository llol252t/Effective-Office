package band.effective.office.tablet.board.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultScreen(
    questionNumber: Int = 2,
    totalQuestions: Int = 5,
    questionText: String = "Утвердить годовой бюджет",
    votesFor: Int = 7,
    votesAgainst: Int = 2,
    votesAbstained: Int = 1
) {
    val totalVotes = votesFor + votesAgainst + votesAbstained

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BoardColors.DarkBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 40.dp,
                    vertical = 8.dp
                )
        ) {

            // Верхняя панель
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Совет директоров",
                    color = BoardColors.WhiteText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(BoardColors.CardBackground)
                        .padding(
                            horizontal = 22.dp,
                            vertical = 10.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(BoardColors.GreenText)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "Подключено к серверу",
                        color = BoardColors.WhiteText,
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Блок принятого решения
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(32.dp))
                    .background(BoardColors.VoteGreen)
                    .padding(
                        horizontal = 36.dp,
                        vertical = 32.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(BoardColors.DarkContent),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        color = BoardColors.VoteGreen,
                        fontSize = 44.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(24.dp))

                Column {
                    Text(
                        text = "Вопрос $questionNumber из $totalQuestions · $questionText",
                        color = BoardColors.DarkContent,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Решение принято",
                        color = BoardColors.DarkContent,
                        fontSize = 40.sp,
                        lineHeight = 44.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Карточка результатов голосования
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(BoardColors.OptionBackground)
                    .padding(28.dp)
            ) {

                VoteResultRow(
                    title = "За",
                    count = votesFor,
                    total = totalVotes,
                    barColor = BoardColors.VoteGreen
                )

                Spacer(modifier = Modifier.height(22.dp))

                VoteResultRow(
                    title = "Против",
                    count = votesAgainst,
                    total = totalVotes,
                    barColor = BoardColors.VoteRed
                )

                Spacer(modifier = Modifier.height(22.dp))

                VoteResultRow(
                    title = "Воздержался",
                    count = votesAbstained,
                    total = totalVotes,
                    barColor = BoardColors.VoteGray
                )

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "Кворум есть · простое большинство · проголосовали $totalVotes из $totalVotes",
                    color = BoardColors.GrayText,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun VoteResultRow(
    title: String,
    count: Int,
    total: Int,
    barColor: Color
) {
    val progress = if (total > 0) {
        (count.toFloat() / total.toFloat())
            .coerceIn(0f, 1f)
    } else {
        0f
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            modifier = Modifier.width(200.dp),
            color = BoardColors.WhiteText,
            fontSize = 20.sp
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .clip(CircleShape)
                .background(BoardColors.SelectedBackground)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .clip(CircleShape)
                    .background(barColor)
            )
        }

        Text(
            text = count.toString(),
            modifier = Modifier
                .width(50.dp)
                .padding(start = 8.dp),
            color = BoardColors.WhiteText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}