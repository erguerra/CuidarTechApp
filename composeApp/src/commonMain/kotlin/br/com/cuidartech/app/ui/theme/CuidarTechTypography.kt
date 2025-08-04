package br.com.cuidartech.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import cuidartechapp.composeapp.generated.resources.Res
import cuidartechapp.composeapp.generated.resources.merriweather_sans_bold
import cuidartechapp.composeapp.generated.resources.merriweather_sans_light
import cuidartechapp.composeapp.generated.resources.merriweather_sans_regular
import cuidartechapp.composeapp.generated.resources.merriweather_sans_semi_bold
import org.jetbrains.compose.resources.Font

val standardTypography = Typography()

@Composable
fun getCuidarTechTypography() =
    Typography(
        standardTypography.displayLarge.copy(fontFamily = getMerriweatherSans()),
        standardTypography.displayMedium.copy(fontFamily = getMerriweatherSans()),
        standardTypography.displaySmall.copy(fontFamily = getMerriweatherSans()),
        standardTypography.headlineLarge.copy(fontFamily = getMerriweatherSans()),
        standardTypography.headlineMedium.copy(fontFamily = getMerriweatherSans()),
        standardTypography.headlineSmall.copy(fontFamily = getMerriweatherSans()),
        standardTypography.titleLarge.copy(fontFamily = getMerriweatherSans()),
        standardTypography.titleMedium.copy(fontFamily = getMerriweatherSans()),
        standardTypography.titleSmall.copy(fontFamily = getMerriweatherSans()),
        standardTypography.bodyLarge.copy(fontFamily = getMerriweatherSans()),
        standardTypography.bodyMedium.copy(fontFamily = getMerriweatherSans()),
        standardTypography.bodySmall.copy(fontFamily = getMerriweatherSans()),
        standardTypography.labelLarge.copy(fontFamily = getMerriweatherSans()),
        standardTypography.labelMedium.copy(fontFamily = getMerriweatherSans()),
        standardTypography.labelSmall.copy(fontFamily = getMerriweatherSans()),
    )

@Composable
fun getMerriweatherSans() =
    FontFamily(
        Font(resource = Res.font.merriweather_sans_regular, weight = FontWeight.Normal),
        Font(resource = Res.font.merriweather_sans_bold, weight = FontWeight.Bold),
        Font(resource = Res.font.merriweather_sans_semi_bold, weight = FontWeight.SemiBold),
        Font(resource = Res.font.merriweather_sans_light, weight = FontWeight.Light),
    )
