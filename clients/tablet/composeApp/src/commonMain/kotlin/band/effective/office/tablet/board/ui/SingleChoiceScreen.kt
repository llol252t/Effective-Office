package band.effective.office.tablet.board.ui

import android.util.Log
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val SingleChoiceBackground = Color(0xFF191A1D)
private val SingleChoiceOptionBackground = Color(0xFF2A2A2D)
private val SingleChoiceSelectedBackground = Color(0xFF353538)
private val SingleChoiceGreen = Color(0xFF36C477)
private val SingleChoiceOrange = Color(0xFFFF7900)
private val SingleChoiceText = Color(0xFFF5F5F5)
private val SingleChoiceMutedText = Color(0xFF96979D)
private val SingleChoiceBorder = Color(0xFF444449)
private val SingleChoiceDisabledOrange = Color(0xFF5B4635)

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
        color = SingleChoiceBackground
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
                    color = SingleChoiceText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFF29292C))
                        .padding(horizontal = 14.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(SingleChoiceGreen)
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = "Подключено к серверу",
                        color = SingleChoiceText,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Номер вопроса
            Text(
                text = "Вопрос $questionNumber из $totalQuestions",
                color = SingleChoiceGreen,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Заголовок вопроса
            Text(
                text = questionText,
                color = Color.White,
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
                                SingleChoiceSelectedBackground
                            } else {
                                SingleChoiceOptionBackground
                            }
                        )
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) {
                                SingleChoiceGreen
                            } else {
                                SingleChoiceBorder
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
                                    SingleChoiceGreen
                                } else {
                                    SingleChoiceMutedText
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
                                    .background(SingleChoiceGreen)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    Text(
                        text = option,
                        color = SingleChoiceText,
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
                    color = SingleChoiceMutedText,
                    fontSize = 13.sp
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            if (selectedOption != null) {
                                SingleChoiceOrange
                            } else {
                                SingleChoiceDisabledOrange
                            }
                        )
                        .clickable(enabled = selectedOption != null) {
                            selectedOption?.let { name ->
                                Log.d(LOG_TAG, "Выбран вариант: $name")
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
                        color = Color(0xFF111113),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
