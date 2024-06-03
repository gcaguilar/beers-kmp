package presentation.authentication

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

object LoginScreen: Screen {
    @Composable
    override fun Content() {
        LoginContent()
    }
}
