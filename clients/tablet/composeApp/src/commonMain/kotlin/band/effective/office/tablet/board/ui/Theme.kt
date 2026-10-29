package band.effective.office.tablet.board.ui

import androidx.compose.ui.graphics.Color

object BoardColors {
    // --- ФОНЫ (взято из md_theme_dark) ---
    val DarkBackground = Color(0xFF1E1C1A)      // md_theme_dark_background
    val CardBackground = Color(0xFF252322)      // md_theme_dark_surface
    val OptionBackground = Color(0xFF302D2C)    // md_theme_dark_elevationBackground
    val SelectedBackground = Color(0xFF3A3736)  // md_theme_dark_mountainBackground

    // --- АКЦЕНТЫ И КНОПКИ ---
    val GreenText = Color(0xFF36C85F)           // md_theme_dark_freeStatus (зеленый статус)
    val VoteGreen = Color(0xFF36C85F)           // Используем тот же зеленый
    val ConfirmOrange = Color(0xFFEE7234)       // md_theme_dark_primary
    val OrangeDisabled = Color(0xFF483E38)      // md_theme_dark_disabledPrimaryButton

    // --- ГОЛОСОВАНИЕ ---
    val VoteRed = Color(0xFFF84C4C)             // md_theme_dark_busyStatus (красный)
    val VoteGray = Color(0xFF808080)            // md_theme_dark_secondaryTextAndIcon (серый)
    val DarkContent = Color(0xFF1E1C1A)         // Темный текст на ярких кнопках (взят фон)

    // --- ТЕКСТ И РАМКИ ---
    val GrayText = Color(0xFF808080)            // md_theme_dark_secondaryTextAndIcon
    val WhiteText = Color(0xFFF9F9F9)           // md_theme_dark_primaryTextAndIcon
    val BorderGray = Color(0xFF6E6E6E)          // md_theme_dark_tertiaryTextAndIcon
}