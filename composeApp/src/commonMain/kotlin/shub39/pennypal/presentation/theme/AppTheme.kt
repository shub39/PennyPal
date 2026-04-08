package shub39.pennypal.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.PaletteStyle

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppTheme(isDark: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    DynamicMaterialTheme(
        seedColor = Color.Black,
        isAmoled = true,
        style = PaletteStyle.Monochrome,
        isDark = isDark,
        content = content
    )
}
