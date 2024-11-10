package com.claudiogalvaodev.moviemanager.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = DarkPurple,
    primaryVariant = DarkPurple,
    secondary = DarkPurple
)

private val LightColorPalette = lightColors(
    primary = DarkPurple,
    primaryVariant = DarkPurple,
    secondary = DarkPurple,
    background = DarkPurple,
    surface = DarkPurple,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun CineSeteTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}