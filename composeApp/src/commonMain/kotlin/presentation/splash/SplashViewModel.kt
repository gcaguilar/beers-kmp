package presentation.splash

import cafe.adriel.voyager.core.model.StateScreenModel
import domain.IsLoggedIn
import kotlinx.coroutines.flow.update


class SplashScreenModel(
    private val isLoggedIn: IsLoggedIn
) : StateScreenModel<SplashScreenModel.SplashState>(SplashState()) {
    data class SplashState(
        val isLoggedIn: Boolean? = null
    )

    fun isLogged() {
        val loggedIn = isLoggedIn()
        mutableState.update { state ->
            state.copy(isLoggedIn = loggedIn)
        }
    }
}
