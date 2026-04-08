package shub39.pennypal

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.plugin.module.dsl.startKoin
import shub39.pennypal.di.Module
import shub39.pennypal.di.PlatformModule

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin<Module> {
            androidLogger()
            androidContext(this@MainApplication)
        }
    }
}