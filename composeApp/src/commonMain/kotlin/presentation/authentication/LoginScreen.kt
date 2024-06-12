 package presentation.authentication

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import di.koinScreenModel
import presentation.search.SearchScreen

private const val FetchAuthenticationUrlKey = "FetchUri"

object LoginScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<LoginScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val context =

        LaunchedEffect(FetchAuthenticationUrlKey) {
            screenModel.getLogin()
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