package presentation.authentication

import android.net.Uri
import android.os.Bundle
import android.provider.Browser
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import presentation.search.SearchScreen

private const val FetchAuthenticationUrlKey = "FetchUri"

actual object LoginScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<LoginScreenModel>()
        val state by screenModel.state.collectAsState()
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(FetchAuthenticationUrlKey) {
            screenModel.getLogin()
        }

        fun signIn(url: String) {
            val customTabsIntent = CustomTabsIntent.Builder()
                .setShowTitle(false)
                .build()

            val headers = Bundle()
            headers.putString("User-Agent", "BeersKMM D912C0B80A28A04F4EA2FD48E625853326BEAB1C")
            customTabsIntent.intent.putExtra(Browser.EXTRA_HEADERS, headers)

            customTabsIntent.launchUrl(context, Uri.parse(url))
        }

        state.events?.let { event ->
            when (event) {
                AuthenticationEvents.LoggedIn -> {
                    navigator.replace(SearchScreen)
                    screenModel.processNavigation()
                }

                is AuthenticationEvents.MakeLogin -> signIn(event.loginUrl)
            }
        }
    }
}