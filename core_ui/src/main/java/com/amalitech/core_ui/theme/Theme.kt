package com.amalitech.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightColors = lightColorScheme(
    primary = primary,
    onPrimary = onPrimary,
    primaryContainer = darkGray,
    secondary = secondary,
    onSecondary = onSecondary,
    tertiary = tertiary,
    onTertiary = onTertiary,
    background = background,
    onBackground = onBackground,
    surface = black,
    outline = link
)


private val DarkColors = darkColorScheme(
    primary = primary,
    onPrimary = onPrimary,
    primaryContainer = darkGray,
    secondary = secondary,
    onSecondary = onSecondary,
    tertiary = tertiary,
    onTertiary = onTertiary,
    background = background,
    onBackground = onBackground,
    surface = black,
    outline = link
)
@Composable
fun BookMeetingRoomTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }
    CompositionLocalProvider(LocalSpacing provides Dimensions()) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}