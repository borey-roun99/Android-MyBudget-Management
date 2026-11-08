package com.mybudget.app.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = DustyRose,
    onPrimary = Color.White,
    primaryContainer = BlushPink,
    onPrimaryContainer = DeepPlum,
    secondary = DeepPlum,
    onSecondary = Color.White,
    secondaryContainer = BlushPink,
    onSecondaryContainer = DeepPlum,
    background = WarmCream,
    onBackground = DeepPlum,
    surface = WarmCream,
    onSurface = DeepPlum,
    surfaceVariant = BlushPink,
    onSurfaceVariant = MutedGrey,
    outline = MutedGrey
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFD995AA),
    onPrimary = Color(0xFF3D202C),
    primaryContainer = Color(0xFF5A3544),
    onPrimaryContainer = Color(0xFFF8DCE5),
    secondary = Color(0xFFE9C3CF),
    onSecondary = Color(0xFF3D202C),
    secondaryContainer = Color(0xFF493842),
    onSecondaryContainer = Color(0xFFF8E5EB),
    background = Color(0xFF211A1E),
    onBackground = Color(0xFFF4E9ED),
    surface = Color(0xFF292127),
    onSurface = Color(0xFFF4E9ED),
    surfaceVariant = Color(0xFF493842),
    onSurfaceVariant = Color(0xFFD0BFC6),
    outline = Color(0xFF9E8791)
)

@Composable
fun MyBudgetTheme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = MyBudgetTypography,
        content = content
    )
}
