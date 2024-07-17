package org.gcaguilar.kmmbeers

import android.app.Application
import di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(networkModule + dataModule + domainModule + presentationModule + platform)
        }
    }
}
