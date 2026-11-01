package band.effective.office.tablet.board.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AnswerAcceptedScreen(
    acceptedCount: Int = 8,
    totalCount: Int = 10,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BoardColors.DarkBackground)
    ) {
        // Header — 72 px
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(horizontal = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(BoardColors.VoteGreen)
                )

                Text(
                    text = "Подключено",
                    color = BoardColors.WhiteText,
                    fontSize = 18.sp
                )
            }
        }

        // Main content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 40.dp,
                    end = 40.dp,
                    top = 8.dp,
                    bottom = 48.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .width(780.dp)
                    .clip(RoundedCornerShape(36.dp))
                    .background(BoardColors.CardBackground)
                    .padding(
                        horizontal = 48.dp,
                        vertical = 52.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Green icon circle — 96 x 96
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(BoardColors.VoteGreen),
                    contentAlignment = Alignment.Center
                ) {
                    CheckMark(
                        modifier = Modifier.size(56.dp)
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "Ваш ответ принят",
                    color = BoardColors.WhiteText,
                    fontSize = 52.sp,
                    lineHeight = 57.2.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = acceptedCount.toString(),
                        color = BoardColors.WhiteText,
                        fontSize = 52.sp,
                        lineHeight = 52.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "из $totalCount ответили",
                        color = BoardColors.GrayText,
                        fontSize = 28.sp,
                        lineHeight = 34.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                // Progress bar — 16 px
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(BoardColors.SelectedBackground)
                ) {
                    val progress = if (totalCount > 0) {
                        (acceptedCount.toFloat() / totalCount.toFloat())
                            .coerceIn(0f, 1f)
                    } else {
                        0f
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress)
                            .height(16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(BoardColors.VoteGreen)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Ожидаем, пока проголосуют все участники",
                    color = BoardColors.GrayText,
                    fontSize = 21.sp,
                    lineHeight = 26.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun CheckMark(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val path = Path().apply {
            moveTo(size.width * 0.22f, size.height * 0.52f)
            lineTo(size.width * 0.43f, size.height * 0.72f)
            lineTo(size.width * 0.80f, size.height * 0.30f)
        }

        drawPath(
            path = path,
            color = BoardColors.DarkContent,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}
@Preview(showBackground = true, widthDp = 800, heightDp = 600)
@Composable
fun mainContent() {
    AnswerAcceptedScreen()
}