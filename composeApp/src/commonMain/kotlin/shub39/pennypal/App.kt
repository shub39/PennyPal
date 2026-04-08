package shub39.pennypal

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.viewmodel.koinViewModel
import shub39.pennypal.presentation.fadeTransitionMetadata
import shub39.pennypal.presentation.home.HomePage
import shub39.pennypal.presentation.theme.AppTheme
import shub39.pennypal.viewmodel.HomeViewModel

@Serializable
sealed interface AppRoutes {
    @Serializable
    data object HomePage : NavKey

    @Serializable
    data object AnalyticsPage : NavKey

    @Serializable
    data object SettingsPage : NavKey
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

        Scaffold { padding ->
            NavDisplay(
                modifier = Modifier.padding(padding),
                backStack = globalBackStack,
                entryProvider = entryProvider {
                    entry<AppRoutes.HomePage>(metadata = fadeTransitionMetadata()) {
                        val viewmodel = koinViewModel<HomeViewModel>()
                        val state by viewmodel.state.collectAsStateWithLifecycle()

                        HomePage(
                            state = state,
                            onAction = viewmodel::onAction,
                        )
                    }
                }
            )
        }
    }
}
