package presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.Authenticate
import domain.GetAuthenticationUrl
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class AuthenticationEvents {
    data class MakeLogin(val loginUrl: String) : AuthenticationEvents()
    data object LoggedIn : AuthenticationEvents()
}

data class AuthenticationUIState(
    val events: AuthenticationEvents? = null,
    val isLoading: Boolean = true
)

class LoginViewModel(
    private val getAuthenticationUrl: GetAuthenticationUrl,
    private val authenticate: Authenticate
) : ViewModel() {
    private val _authenticationUIState = MutableStateFlow(AuthenticationUIState())
    val authenticationUIState: StateFlow<AuthenticationUIState> = _authenticationUIState.asStateFlow()

    fun getLogin() {
        _authenticationUIState.update { uiState ->
            uiState.copy(
                isLoading = true,
            )
        }
        viewModelScope.launch {
            getAuthenticationUrl()
                .fold({ url ->
                    _authenticationUIState.update { uiState ->
                        uiState.copy(
                            events = AuthenticationEvents.MakeLogin(url),
                            isLoading = false
                        )
                    }
                }, {

                })
        }
    }

    fun receivedCode(intentCode: String) {
        _authenticationUIState.update { uiState ->
            uiState.copy(
                isLoading = true,
            )
        }
        viewModelScope.launch {
            authenticate(intentCode).fold(
                onSuccess = {
                    _authenticationUIState.update {
                        it.copy(
                            events = AuthenticationEvents.LoggedIn,
                            isLoading = false,
                        )
                    }
                },
                onFailure = {
                    Napier.e("Authentication failed")
                }
            )
        }
    }

    fun processNavigation() {
        _authenticationUIState.update {
            it.copy(
                events = null
            )
        }
    }
}
