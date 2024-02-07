package org.example.project

import android.app.Application
import di.commonModule
import di.sharedModule
import org.example.project.di.androidModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.koinApplication

class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@MainApplication)
            modules(androidModule, sharedModule, commonModule)
        }
    }
}