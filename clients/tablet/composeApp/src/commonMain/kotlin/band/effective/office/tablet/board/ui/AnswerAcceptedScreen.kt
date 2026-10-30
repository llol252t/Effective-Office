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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnswerAcceptedScreen(
    acceptedCount: Int = 8,
    totalCount: Int = 10
) {
    val progress = if (totalCount > 0) {
        (acceptedCount.toFloat() / totalCount.toFloat()).coerceIn(0f, 1f)
    } else {
        0f
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BoardColors.DarkBackground
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(horizontal = 40.dp),
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
                        .padding(horizontal = 22.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(BoardColors.GreenText)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = "Подключено к серверу",
                        color = BoardColors.WhiteText,
                        fontSize = 18.sp
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 40.dp, end = 40.dp, top = 8.dp, bottom = 48.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(36.dp))
                        .background(BoardColors.CardBackground)
                        .padding(horizontal = 48.dp, vertical = 52.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .background(BoardColors.GreenText),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✓",
                            color = BoardColors.DarkContent,
                            fontSize = 56.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Text(
                        text = "Ваш ответ принят",
                        color = BoardColors.WhiteText,
                        fontSize = 52.sp,
                        lineHeight = 58.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = acceptedCount.toString(),
                            color = BoardColors.WhiteText,
                            fontSize = 52.sp,
                            lineHeight = 52.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = " из $totalCount ответили",
                            color = BoardColors.GrayText,
                            fontSize = 28.sp,
                            lineHeight = 34.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(22.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(BoardColors.SelectedBackground)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progress)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(8.dp))
                                .background(BoardColors.GreenText)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Ожидаем, пока проголосуют все участники",
                        color = BoardColors.GrayText,
                        fontSize = 21.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
