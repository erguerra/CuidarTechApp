package br.com.cuidartech.app.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val colorPrimary = Color(0xFF043938E)
private val surfaceVariant = Color(0xFFC7E5E4)

private val background = Color(0xFFFDFDFD)
private val onBackground = Color(0xFF333333)

// Blue colors for splash screen
val Blue400 = Color(0xFF42A5F5)
val Blue600 = Color(0xFF1E88E5)
val Blue800 = Color(0xFF1565C0)

val LightColorScheme =
    lightColorScheme(
        primary = colorPrimary,
        onPrimary = Color.White,
        surfaceVariant = surfaceVariant,
        background = background,
        onBackground = onBackground,
    )
