package shub39.pennypal

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.Text
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
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import pennypal.composeapp.generated.resources.Res
import pennypal.composeapp.generated.resources.add
import pennypal.composeapp.generated.resources.analytics
import pennypal.composeapp.generated.resources.home
import pennypal.composeapp.generated.resources.settings
import shub39.pennypal.presentation.fadeTransitionMetadata
import shub39.pennypal.presentation.home.HomePage
import shub39.pennypal.presentation.theme.AppTheme
import shub39.pennypal.viewmodel.HomeViewModel

@Serializable
sealed interface AppRoutes: NavKey {
    @Serializable data object HomePage : AppRoutes

    @Serializable data object AnalyticsPage : AppRoutes

    @Serializable data object SettingsPage : AppRoutes
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

        Scaffold(
            bottomBar = {
                NavigationBar {
                    listOf(
                        AppRoutes.HomePage,
                        AppRoutes.AnalyticsPage,
                        AppRoutes.SettingsPage
                    ).forEach { route ->
                        NavigationBarItem(
                            selected = globalBackStack.last() == route,
                            onClick = {
                                globalBackStack.removeAll { it == route }
                                globalBackStack.add(route)
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(
                                        when (route) {
                                            AppRoutes.AnalyticsPage -> Res.drawable.analytics
                                            AppRoutes.HomePage -> Res.drawable.home
                                            AppRoutes.SettingsPage -> Res.drawable.settings
                                        }
                                    ),
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(
                                    text = when (route) {
                                        AppRoutes.AnalyticsPage -> "Analytics"
                                        AppRoutes.HomePage -> "Home"
                                        AppRoutes.SettingsPage -> "Settings"
                                    }
                                )
                            },
                            alwaysShowLabel = true
                        )
                    }
                }
            }
        ) { padding ->
            NavDisplay(
                modifier = Modifier.padding(padding),
                backStack = globalBackStack,
                entryProvider =
                    entryProvider {
                        entry<AppRoutes.HomePage>(metadata = fadeTransitionMetadata()) {
                            val viewmodel = koinViewModel<HomeViewModel>()
                            val state by viewmodel.state.collectAsStateWithLifecycle()

                            HomePage(state = state, onAction = viewmodel::onAction)
                        }

                        entry<AppRoutes.AnalyticsPage>(metadata = fadeTransitionMetadata()) {

                        }

                        entry<AppRoutes.SettingsPage>(metadata = fadeTransitionMetadata()) {

                        }
                    },
            )
        }
    }
}
