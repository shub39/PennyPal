package shub39.pennypal

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.plugin.module.dsl.startKoin
import shub39.pennypal.di.Module

fun MainViewController() {
    startKoin<Module>()

    ComposeUIViewController { App() }
}