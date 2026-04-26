package com.ElOuedUniv.maktaba.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Dark Color Scheme configuration
private val DarkColorScheme = darkColorScheme(
    primary = Green80,                // Light green for better visibility on dark backgrounds
    onPrimary = DarkGreen90,          // Contrast text for primary color
    primaryContainer = DarkGreen80,   // Subtle green container for cards/buttons
    onPrimaryContainer = Green80,     // High contrast text for containers
    secondary = Green60,              // Secondary accent color
    onSecondary = White,
    background = DarkGreen90,         // Main background color for dark mode
    onBackground = White,
    surface = DarkGreen80,            // Surfaces like cards and menus
    onSurface = White,
    surfaceVariant = DarkGreen60,     // Alternative surface color
    onSurfaceVariant = LightGray      // Subtle text/icons on surfaces
)

// Light Color Scheme configuration
private val LightColorScheme = lightColorScheme(
    primary = Green40,                // Deep green for main elements
    onPrimary = White,                // White text on deep green
    primaryContainer = LightGreen,    // Soft green background for highlighted sections
    onPrimaryContainer = Green40,     // Deep green text for light containers
    secondary = Green60,              // Medium green for accents
    onSecondary = White,
    background = White,               // Clean white background for light mode
    onBackground = DarkGreen90,       // Dark text for readability
    surface = LightGreen,             // Very light green for cards/sheets
    onSurface = DarkGreen90,          // Dark text on surfaces
    surfaceVariant = LightGray,       // Subtle contrast for inputs/borders
    onSurfaceVariant = DarkGreen60    // Muted text for secondary info
)

@Composable
fun MaktabaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            
            // Set Status Bar color to match the theme background
            window.statusBarColor = colorScheme.background.toArgb()
            
            // Adjust Status Bar icons (Light/Dark) based on the current theme
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
