package shub39.pennypal.di

import org.koin.dsl.KoinAppDeclaration
import org.koin.plugin.module.dsl.startKoin

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin<Modules> { config?.invoke(this) }
}
