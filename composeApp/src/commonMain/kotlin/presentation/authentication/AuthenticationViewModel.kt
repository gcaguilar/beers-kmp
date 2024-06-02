package presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.GetAuthenticationUrl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class AuthenticationEvents {
    data class MakeLogin(val loginUrl: String) : AuthenticationEvents()
}

data class AuthenticationUIState(
    val events: AuthenticationEvents? = null
)

class AuthenticationViewModel(
    private val getAuthenticationUrl: GetAuthenticationUrl
) : ViewModel() {
    private val _authenticationUIState = MutableStateFlow(AuthenticationUIState())
    val authenticationUIState: StateFlow<AuthenticationUIState> = _authenticationUIState.asStateFlow()

    fun getLogin() {
        viewModelScope.launch {
            getAuthenticationUrl()
                .fold({ url ->
                    _authenticationUIState.update { uiState ->
                        uiState.copy(
                            events = AuthenticationEvents.MakeLogin(url)
                        )
                    }
                }, {

                })
        }
    }

    fun handleResponse(code: String) {

    }
}
