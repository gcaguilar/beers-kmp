package presentation.authentication

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.Authenticate
import io.github.aakira.napier.Napier
import org.koin.compose.koinInject
import presentation.search.SearchScreen

data class AuthenticationScreen(val code: String?) : Screen {
    @Composable
    override fun Content() {
        AuthenticationContent(code)
    }
}

@Composable
fun AuthenticationContent(
    code: String?,
    authenticate: Authenticate = koinInject()
) {
    val navigator = LocalNavigator.currentOrThrow

    CircularProgressIndicator()

    code?.let {
        LaunchedEffect(Unit) {
            authenticate(it).fold({
                navigator.replace(SearchScreen)
            }, {
                Napier.e("Error while authenticating $code", it)
            })
        }
    }
}
