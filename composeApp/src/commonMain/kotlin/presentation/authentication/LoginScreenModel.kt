package presentation.authentication


import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.Authenticate
import domain.GetAuthenticationUrl
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class AuthenticationEvents {
    data class MakeLogin(val loginUrl: String) : AuthenticationEvents()
    data object LoggedIn : AuthenticationEvents()
}

class LoginScreenModel(
    private val getAuthenticationUrl: GetAuthenticationUrl,
    private val authenticate: Authenticate
) : StateScreenModel<LoginScreenModel.AuthenticationUIState>(AuthenticationUIState()) {
    data class AuthenticationUIState(
        val events: AuthenticationEvents? = null,
        val isLoading: Boolean = true
    )

    fun getLogin() {
        mutableState.update { uiState ->
            uiState.copy(
                isLoading = true,
            )
        }
        screenModelScope.launch {
            getAuthenticationUrl()
                .fold({ url ->
                    mutableState.update { uiState ->
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
        mutableState.update { uiState ->
            uiState.copy(
                isLoading = true,
            )
        }
        screenModelScope.launch {
            authenticate(intentCode).fold(
                onSuccess = {
                    mutableState.update {
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
        mutableState.update {
            it.copy(
                events = null
            )
        }
    }
}
