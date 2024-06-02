package presentation.authentication

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

object AuthenticationScreen : Screen {
    @Composable
    override fun Content() {
        AuthenticationContent()
    }
}

@Composable
expect fun AuthenticationContent()


