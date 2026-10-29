package band.effective.office.tablet.board.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val LOG_TAG = "SingleChoiceScreen"

@Composable
fun SingleChoiceScreen(
    questionNumber: Int = 4,
    totalQuestions: Int = 5,
    questionText: String = "Выбрать корпоративного секретаря",
    onConfirm: (String) -> Unit = {}
) {
    val options = listOf(
        "Мария Орлова",
        "Дмитрий Лебедев",
        "Андрей Захаров"
    )

    var selectedOption by remember {
        mutableStateOf<String?>(null)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BoardColors.DarkBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(26.dp)
        ) {
            // Верхняя часть экрана
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Совет директоров",
                    color = BoardColors.WhiteText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(BoardColors.CardBackground) // Заменили хардкод
                        .padding(horizontal = 14.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(BoardColors.GreenText)
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = "Подключено к серверу",
                        color = BoardColors.WhiteText,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Номер вопроса
            Text(
                text = "Вопрос $questionNumber из $totalQuestions",
                color = BoardColors.GreenText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Заголовок вопроса
            Text(
                text = questionText,
                color = BoardColors.WhiteText,
                fontSize = 30.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(26.dp))

            // Список вариантов
            options.forEach { option ->
                val isSelected = selectedOption == option
                val shape = RoundedCornerShape(30.dp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .clip(shape)
                        .background(
                            if (isSelected) {
                                BoardColors.SelectedBackground
                            } else {
                                BoardColors.OptionBackground
                            }
                        )
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) {
                                BoardColors.GreenText
                            } else {
                                BoardColors.BorderGray
                            },
                            shape = shape
                        )
                        .clickable {
                            selectedOption = option
                        }
                        .padding(
                            horizontal = 24.dp,
                            vertical = 16.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Радиокнопка
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .border(
                                width = 2.dp,
                                color = if (isSelected) {
                                    BoardColors.GreenText
                                } else {
                                    BoardColors.GrayText
                                },
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(BoardColors.GreenText)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    Text(
                        text = option,
                        color = BoardColors.WhiteText,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            // Свободное пространство до нижней панели
            Spacer(modifier = Modifier.weight(1f))

            // Нижняя панель
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Проверьте выбор перед отправкой",
                    color = BoardColors.GrayText,
                    fontSize = 13.sp
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            if (selectedOption != null) {
                                BoardColors.ConfirmOrange
                            } else {
                                BoardColors.OrangeDisabled
                            }
                        )
                        .clickable(enabled = selectedOption != null) {
                            selectedOption?.let { name ->
                                println("[$LOG_TAG] Выбран вариант: $name")
                                onConfirm(name)
                            }
                        }
                        .padding(
                            horizontal = 36.dp,
                            vertical = 16.dp
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Подтвердить выбор",
                        color = BoardColors.DarkContent,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 800,
    heightDp = 600,
    name = "Экран выбора из списка"
)
@Composable
fun SingleChoiceScreenPreview() {
    SingleChoiceScreen(
        questionNumber = 4,
        totalQuestions = 5,
        questionText = "Выбрать корпоративного секретаря"
    )
}