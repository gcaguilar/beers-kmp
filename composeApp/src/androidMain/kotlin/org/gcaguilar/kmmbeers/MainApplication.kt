package org.gcaguilar.kmmbeers

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.gcaguilar.kmmbeers.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val options = FirebaseOptions.Builder()
            .setProjectId("beerkmp-90e6b")
            .setApiKey("AIzaSyDy7IsEjNH4xA_98zrCHkdHB1xne3TKRC8")
            .setApplicationId("1:550364172787:android:9ef021da59e7fea2f90286")
            .build()
        FirebaseApp.initializeApp(this, options)
        startKoin {
            androidContext(applicationContext)
            modules(networkModule + dataModule + domainModule + presentationModule + platform)
        }
    }
}
