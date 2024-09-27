package com.example.railtech.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
onPrimary = onPrimaryDark,
primaryContainer = primaryContainerDark,
onPrimaryContainer = onPrimaryContainerDark,
secondary = secondaryDark,
onSecondary = onSecondaryDark,
secondaryContainer = secondaryContainerDark,
onSecondaryContainer = onSecondaryContainerDark,
tertiary = tertiaryDark,
onTertiary = onTertiaryDark,
tertiaryContainer = tertiaryContainerDark,
onTertiaryContainer = onTertiaryContainerDark,
error = errorDark,
errorContainer = errorContainerDark,
onError = onErrorDark,
onErrorContainer = onErrorContainerDark,
background = backgroundDark,
onBackground = onBackgroundDark,
surface = surfaceDark,
onSurface = onSurfaceDark,
surfaceVariant = surfaceVariantDark,
onSurfaceVariant = onSurfaceVariantDark,
outline = outlineDark,
inverseOnSurface = inverseOnSurfaceDark,
inverseSurface = inverseSurfaceDark,
inversePrimary = inversePrimaryDark,
surfaceTint = primaryDark, // Assuming you want to keep primaryDark for surfaceTint
outlineVariant = outlineVariantDark,
scrim = scrimDark
)


val LightColorScheme = lightColorScheme(
onPrimary = onPrimaryLight,
primaryContainer = primaryContainerLight,
onPrimaryContainer = onPrimaryContainerLight,
secondary = secondaryLight,
onSecondary = onSecondaryLight,
secondaryContainer = secondaryContainerLight,
onSecondaryContainer = onSecondaryContainerLight,
tertiary = tertiaryLight,
onTertiary = onTertiaryLight,
tertiaryContainer = tertiaryContainerLight,
onTertiaryContainer = onTertiaryContainerLight,
error = errorLight,
errorContainer = errorContainerLight,
onError = onErrorLight,
onErrorContainer = onErrorContainerLight,
background = backgroundLight,
onBackground = onBackgroundLight,
surface = surfaceLight,
onSurface = onSurfaceLight,
surfaceVariant = surfaceVariantLight,
onSurfaceVariant = onSurfaceVariantLight,
outline = outlineLight,
inverseOnSurface = inverseOnSurfaceLight,
inverseSurface = inverseSurfaceLight,
inversePrimary = inversePrimaryLight,
surfaceTint = primaryLight, // Assuming you want to keep primaryLight for surfaceTint
outlineVariant = outlineVariantLight,
scrim = scrimLight
)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */


@Composable
fun RailtechTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}