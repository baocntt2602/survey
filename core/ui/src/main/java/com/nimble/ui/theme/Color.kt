package com.nimble.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val DarkColorPalette = AppColors(
  colorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
  )
)

val LightColorPalette = AppColors(
  colorScheme = lightColorScheme(
    primary = Color.Gray,
    onPrimary = Color.White,
    background = Color.White,
    onBackground = Color.Black,
    onPrimaryContainer = PurpleGrey40,
    surface = Color.Gray,
    onSurfaceVariant = Color.White.copy(alpha = 0.5f),
    error = Color.Red.copy(alpha = .5f)
  )
)

data class AppColors(
  val colorScheme: ColorScheme,
)

internal val LocalAppColors = staticCompositionLocalOf { LightColorPalette }
