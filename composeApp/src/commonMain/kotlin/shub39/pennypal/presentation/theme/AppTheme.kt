package shub39.pennypal.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors =
    darkColorScheme(
        primary = Color(0xFFEAEAEA),
        onPrimary = Color(0xFF121212),
        primaryContainer = Color(0xFF2C2C2E),
        onPrimaryContainer = Color(0xFFEAEAEA),
        inversePrimary = Color(0xFF2C2C2E),
        secondary = Color(0xFF2A2A2D),
        onSecondary = Color(0xFFEAEAEA),
        secondaryContainer = Color(0xFF1F1F22),
        onSecondaryContainer = Color(0xFFD1D1D6),
        tertiary = Color(0xFF3A3A3C),
        onTertiary = Color(0xFFEAEAEA),
        tertiaryContainer = Color(0xFF2C2C2E),
        onTertiaryContainer = Color(0xFFD1D1D6),
        background = Color(0xFF0E0E10),
        onBackground = Color(0xFFEAEAEA),
        surface = Color(0xFF1A1A1D),
        onSurface = Color(0xFFEAEAEA),
        surfaceVariant = Color(0xFF2A2A2D),
        onSurfaceVariant = Color(0xFF9A9AA0),
        surfaceDim = Color(0xFF121214),
        surfaceBright = Color(0xFF2C2C30),
        surfaceContainerLowest = Color(0xFF0E0E10),
        surfaceContainerLow = Color(0xFF151517),
        surfaceContainer = Color(0xFF1A1A1D),
        surfaceContainerHigh = Color(0xFF202024),
        surfaceContainerHighest = Color(0xFF2A2A2D),
        outline = Color(0xFF3A3A3C),
        outlineVariant = Color(0xFF2C2C2E),
        error = Color(0xFFFF453A),
        onError = Color(0xFF000000),
        errorContainer = Color(0xFF5C1B16),
        onErrorContainer = Color(0xFFFFDAD6),
        scrim = Color(0xFF000000),
        inverseSurface = Color(0xFFEAEAEA),
        inverseOnSurface = Color(0xFF1A1A1D),
        surfaceTint = Color(0xFFEAEAEA),
    )

private val LightColors =
    lightColorScheme(
        primary = Color(0xFF1A1A1D),
        onPrimary = Color(0xFFFFFFFF),
        primaryContainer = Color(0xFFE9E9ED),
        onPrimaryContainer = Color(0xFF1A1A1D),
        inversePrimary = Color(0xFFEAEAEA),
        secondary = Color(0xFFF2F2F5),
        onSecondary = Color(0xFF1A1A1D),
        secondaryContainer = Color(0xFFE5E5EA),
        onSecondaryContainer = Color(0xFF2C2C2E),
        tertiary = Color(0xFFD1D1D6),
        onTertiary = Color(0xFF1A1A1D),
        tertiaryContainer = Color(0xFFE9E9ED),
        onTertiaryContainer = Color(0xFF2C2C2E),
        background = Color(0xFFF7F7F8),
        onBackground = Color(0xFF1A1A1D),
        surface = Color(0xFFFFFFFF),
        onSurface = Color(0xFF1A1A1D),
        surfaceVariant = Color(0xFFE9E9ED),
        onSurfaceVariant = Color(0xFF6E6E73),
        surfaceDim = Color(0xFFE5E5EA),
        surfaceBright = Color(0xFFFFFFFF),
        surfaceContainerLowest = Color(0xFFFFFFFF),
        surfaceContainerLow = Color(0xFFF7F7F8),
        surfaceContainer = Color(0xFFF2F2F5),
        surfaceContainerHigh = Color(0xFFEDEDF0),
        surfaceContainerHighest = Color(0xFFE5E5EA),
        outline = Color(0xFFD1D1D6),
        outlineVariant = Color(0xFFE5E5EA),
        error = Color(0xFFD92D20),
        onError = Color(0xFFFFFFFF),
        errorContainer = Color(0xFFFFDAD6),
        onErrorContainer = Color(0xFF410002),
        scrim = Color(0x66000000),
        inverseSurface = Color(0xFF1A1A1D),
        inverseOnSurface = Color(0xFFF7F7F8),
        surfaceTint = Color(0xFF1A1A1D),
    )

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppTheme(isDark: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialExpressiveTheme(
        colorScheme = if (isDark) DarkColors else LightColors,
        content = content,
    )
}
