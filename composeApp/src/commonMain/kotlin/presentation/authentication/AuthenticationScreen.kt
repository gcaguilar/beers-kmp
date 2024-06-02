package presentation.authentication

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import org.koin.compose.viewmodel.koinViewModel

object AuthenticationScreen : Screen {
    @Composable
    override fun Content() {
        AuthenticationContent()
    }
}

@Composable
expect fun AuthenticationContent()


