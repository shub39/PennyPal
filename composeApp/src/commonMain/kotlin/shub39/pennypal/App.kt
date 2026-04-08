package shub39.pennypal

import androidx.compose.runtime.*
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import shub39.pennypal.presentation.theme.AppTheme

@Serializable
sealed interface AppRoutes {
    @Serializable
    data object HomePage: NavKey

    @Serializable
    data object AnalyticsPage: NavKey

    @Serializable
    data object SettingsPage: NavKey
}

val appConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(AppRoutes.HomePage::class, AppRoutes.HomePage.serializer())
            subclass(AppRoutes.AnalyticsPage::class, AppRoutes.AnalyticsPage.serializer())
            subclass(AppRoutes.SettingsPage::class, AppRoutes.SettingsPage.serializer())
        }
    }
}

@Composable
fun App() {
    AppTheme {
        val globalBackStack = rememberNavBackStack(appConfig, AppRoutes.HomePage)

        NavDisplay(
            backStack = globalBackStack,
            entryProvider = entryProvider {

            }
        )
    }
}
