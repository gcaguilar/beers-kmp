package org.gcaguilar.kmmbeers

import android.app.Application
import di.initKoin
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}