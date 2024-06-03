package presentation.authentication

import android.net.Uri
import android.os.Bundle
import android.provider.Browser
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.koinInject
import presentation.search.SearchScreen

private const val FetchAuthenticationUrlKey = "FetchUri"

@Composable
actual fun LoginContent() {
    val viewModel: LoginViewModel = koinInject<LoginViewModel>()
    val context = LocalContext.current
    val state = viewModel.authenticationUIState.collectAsState().value
    val navigator = LocalNavigator.currentOrThrow

    LaunchedEffect(FetchAuthenticationUrlKey) {
        viewModel.getLogin()
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
                viewModel.processNavigation()
            }

            is AuthenticationEvents.MakeLogin -> signIn(event.loginUrl)
        }
    }
}
