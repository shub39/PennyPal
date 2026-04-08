package shub39.pennypal

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.plugin.module.dsl.startKoin
import shub39.pennypal.di.Modules

fun MainViewController() {
    startKoin<Modules>()

    ComposeUIViewController { App() }
}