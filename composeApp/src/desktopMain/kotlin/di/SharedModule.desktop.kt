package di

import core.DriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val sharedModule: Module = module {
    single { DriverFactory() }
}