package com.pradeep.storelabassignment.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = StoreLabPurpleDark,
    secondary = StoreLabTeal,
    background = StoreLabBackground,
    surface = StoreLabSurface,
    onPrimary = StoreLabOnPrimary,
    onSecondary = Color.Black,
    onBackground = StoreLabOnSurface,
    onSurface = StoreLabOnSurface,
)

private val DarkColorScheme = darkColorScheme(
    primary = StoreLabPurpleDarkTheme,
    secondary = StoreLabTealDarkTheme,
    background = StoreLabBackgroundDark,
    surface = StoreLabSurfaceDark,
    onPrimary = StoreLabOnPrimaryDark,
    onSecondary = Color.Black,
    onBackground = StoreLabOnSurfaceDark,
    onSurface = StoreLabOnSurfaceDark,
)

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}
