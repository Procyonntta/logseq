package com.logseq.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

@Composable
fun LogseqComposeTheme(content: @Composable () -> Unit) {
    val colors by LogseqTheme.colors.collectAsState()

    val colorScheme = if (colors.isDark) {
        darkColorScheme(
            primary = Color(colors.tint),
            onPrimary = Color(colors.background),
            background = Color(colors.background),
            surface = Color(colors.background),
            onSurface = Color(colors.tint),
            outline = Color(colors.tint).copy(alpha = 0.3f),
        )
    } else {
        lightColorScheme(
            primary = Color(colors.tint),
            onPrimary = Color(colors.background),
            background = Color(colors.background),
            surface = Color(colors.background),
            onSurface = Color(colors.tint),
            outline = Color(colors.tint).copy(alpha = 0.3f),
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
