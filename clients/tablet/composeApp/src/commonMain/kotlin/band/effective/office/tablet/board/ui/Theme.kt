package band.effective.office.tablet.board.ui

import androidx.compose.ui.graphics.Color

object BoardColors {
    // --- ФОНЫ ---
    val DarkBackground = Color(0xFF1E1E1E)      // Главный фон всех экранов
    val CardBackground = Color(0xFF2C2C2C)      // Фон карточек (заседание, участник)
    val OptionBackground = Color(0xFF2A2A2D)    // Фон невыбранных вариантов в списке
    val SelectedBackground = Color(0xFF353538)  // Фон выбранного варианта в списке

    // --- АКЦЕНТЫ И КНОПКИ ---
    val GreenText = Color(0xFF4CAF50)           // Зеленый текст, статус, кнопка "За"
    val VoteGreen = Color(0xFF36C477)           // Зеленый для рамки/кружка выбора
    val ConfirmOrange = Color(0xFFFF7A1A)       // Оранжевая кнопка "Подтвердить"
    val OrangeDisabled = Color(0xFF5B4635)      // Неактивная оранжевая кнопка

    // --- ГОЛОСОВАНИЕ ---
    val VoteRed = Color(0xFFF0525A)             // Красная кнопка "Против"
    val VoteGray = Color(0xFFC9C9CE)            // Серая кнопка "Воздержался"
    val DarkContent = Color(0xFF111113)         // Темный текст на ярких кнопках

    // --- ТЕКСТ И РАМКИ ---
    val GrayText = Color(0xFF96979D)            // Серый текст (подписи, даты)
    val WhiteText = Color(0xFFF5F5F5)           // Белый текст (заголовки, имена)
    val BorderGray = Color(0xFF444449)          // Тонкие рамки вокруг карточек
}