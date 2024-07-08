package presentation.authentication

import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.Browser
import androidx.browser.customtabs.CustomTabsIntent

internal object AppContext {
    private lateinit var application: Application

    fun setUp(context: Context) {
        application = context as Application
    }

    fun get(): Context {
        if(::application.isInitialized.not()) throw Exception("Application context isn't initialized")
        return application.applicationContext
    }
}

actual fun signIn(url: String) {
    val customTabsIntent = CustomTabsIntent.Builder()
        .setShowTitle(false)
        .build()

    val headers = Bundle()
    headers.putString("User-Agent", "BeersKMM D912C0B80A28A04F4EA2FD48E625853326BEAB1C")
    customTabsIntent.intent.putExtra(Browser.EXTRA_HEADERS, headers)

    customTabsIntent.launchUrl(AppContext.get(), Uri.parse(url))
}