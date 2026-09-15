package com.rh.daily.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Bg = Color(0xFF0B0D10)
val Card = Color(0xFF15181D)
val SecondaryCard = Color(0xFF20242B)
val White = Color(0xFFF5F7FA)
val Muted = Color(0xFF8D949E)
val Accent = Color(0xFFB7F36B)

private val DarkColors = darkColorScheme(
    primary = Accent,
    onPrimary = Color(0xFF172000),
    secondary = Accent,
    onSecondary = Color(0xFF172000),
    background = Bg,
    onBackground = White,
    surface = Card,
    onSurface = White,
    outline = Color(0xFF2B3038),
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF4B6F00),
    onPrimary = Color.White,
    secondary = Color(0xFF4B6F00),
    background = Color(0xFFF6F7F3),
    onBackground = Color(0xFF171A14),
    surface = Color.White,
    onSurface = Color(0xFF171A14),
    outline = Color(0xFFD5D9CE),
)

@Composable
fun RHDailyTheme(darkTheme: Boolean = true, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content,
    )
}
