package presentation.splash

import androidx.lifecycle.ViewModel
import domain.IsLoggedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

data class SplashState(
    val isLoggedIn: Boolean? = null
)

class SplashViewModel(
    private val isLoggedIn: IsLoggedIn
) : ViewModel(), KoinComponent {
    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state.asStateFlow()

    fun isLogged() {
        val loggedIn = isLoggedIn()
        _state.update { state ->
            state.copy(isLoggedIn = loggedIn)
        }
    }
}
