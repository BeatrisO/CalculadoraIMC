package com.seuprojeto.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2D7A63),
    onPrimary = Color.White,

    background = Color(0xFFD1DCD1),
    onBackground = Color.Black,

    surface = Color.White,
    onSurface = Color.Black,

    error = Color(0xFFB00020),
    onError = Color.White
)

@Composable
fun ImcTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography(),
        content = content
    )
}
