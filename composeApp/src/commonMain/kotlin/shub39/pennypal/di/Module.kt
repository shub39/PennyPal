package shub39.pennypal.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [PlatformModule::class])
@ComponentScan("shub39.pennypal")
class Module {

}