package org.gcaguilar.kmmbeers.presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.gcaguilar.kmmbeers.domain.Authenticate
import org.gcaguilar.kmmbeers.domain.IsLoggedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class AuthenticationEvents {
    data object MakeLogin : AuthenticationEvents()
    data object LoggedIn : AuthenticationEvents()
}

class LoginScreenModel(
    private val isLoggedIn: IsLoggedIn,
    private val authenticate: Authenticate
) : ViewModel() {
    private val _state = MutableStateFlow(AuthenticationUIState())
    val state: StateFlow<AuthenticationUIState> = _state

    data class AuthenticationUIState(
        val events: AuthenticationEvents? = null,
        val isLoading: Boolean = true
    )

    fun checkStatus() {
        _state.update { uiState ->
            uiState.copy(
                isLoading = true,
            )
        }
        viewModelScope.launch {
            if (!isLoggedIn()) {
                authenticate()
            }

            _state.update {
                it.copy(
                    events = AuthenticationEvents.LoggedIn
                )
            }
        }
    }


    fun processNavigation() {
        _state.update {
            it.copy(
                events = null
            )
        }
    }
}
