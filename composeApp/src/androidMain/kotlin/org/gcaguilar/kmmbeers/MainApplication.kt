package org.gcaguilar.kmmbeers

import android.app.Application
import com.google.firebase.FirebaseApp
import org.gcaguilar.kmmbeers.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@MainApplication)
            modules(networkModule + dataModule + domainModule + presentationModule + platform)
        }
    }
}
