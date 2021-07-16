package com.example.swiggyapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

val LightColorPalette = lightColors(
    primary = Orange400,
    primaryVariant = Orange600,
    surface = WhiteSurface
    // Using default values for onPrimary, surface, error, etc.
)

val DarkColorPalette = darkColors(
    primary = Orange400,
    primaryVariant = Orange600,
    // secondaryVariant == secondary in dark theme
)

@Composable
fun SwiggyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
//        DarkColorPalette
        LightColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
    )
}