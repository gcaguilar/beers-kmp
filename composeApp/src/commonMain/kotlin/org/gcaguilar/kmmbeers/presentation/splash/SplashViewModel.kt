package org.gcaguilar.kmmbeers.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.gcaguilar.kmmbeers.domain.IsLoggedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import co.touchlab.kermit.Logger


class SplashViewModel(
    private val isLoggedIn: IsLoggedIn
) : ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state

    data class SplashState(
        val isLoggedIn: Boolean? = null
    )

    fun isLogged() {
        viewModelScope.launch {
            val loggedIn = isLoggedIn()
            _state.update { state ->
                state.copy(isLoggedIn = loggedIn)
            }
        }
    }
}
