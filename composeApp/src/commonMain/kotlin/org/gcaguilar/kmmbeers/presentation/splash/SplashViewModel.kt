package org.gcaguilar.kmmbeers.presentation.splash

import androidx.lifecycle.ViewModel
import org.gcaguilar.kmmbeers.domain.IsLoggedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class SplashScreenModel(
    //private val isLoggedIn: IsLoggedIn
) : ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state
    data class SplashState(
        val isLoggedIn: Boolean? = null
    )

    fun isLogged() {
        val loggedIn = false
        _state.update { state ->
            state.copy(isLoggedIn = loggedIn)
        }
    }
}
