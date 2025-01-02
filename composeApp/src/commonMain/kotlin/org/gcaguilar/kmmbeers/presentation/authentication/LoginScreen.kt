package org.gcaguilar.kmmbeers.presentation.authentication

import androidx.compose.runtime.*
import com.tweener.passage.Passage
import com.tweener.passage.model.GoogleGatekeeperAndroidConfiguration
import com.tweener.passage.model.GoogleGatekeeperConfiguration
import com.tweener.passage.rememberPassage
import dev.gitlive.firebase.Firebase
import kotlinx.coroutines.launch
import org.gcaguilar.kmmbeers.presentation.Screen
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    navigate: (String) -> Unit,
    viewModel: LoginViewModel = koinViewModel<LoginViewModel>(),
    firebase: Firebase = koinInject<Firebase>()
) {
    val state by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val gatekeeperConfigurations = listOf(
        GoogleGatekeeperConfiguration(
            serverClientId = "550364172787-1he86uhtnthdhron339d94m7tb38om9d.apps.googleusercontent.com",
            android = GoogleGatekeeperAndroidConfiguration(
                filterByAuthorizedAccounts = false,
                autoSelectEnabled = true,
                maxRetries = 3
            )
        ),
    )
    val passage: Passage = rememberPassage()
    passage.initialize(
        gatekeeperConfigurations = gatekeeperConfigurations,
        firebase = firebase
    )
    LaunchedEffect(Unit) {
        viewModel.checkStatus()
    }

    state.events?.let { it ->
        when (it) {
            AuthenticationEvents.LoggedIn -> {
                navigate(Screen.Search.route)
            }

            else -> {
                coroutineScope.launch {
                    val result = passage.authenticateWithGoogle()

                    result.fold(
                        onSuccess = { _ -> viewModel.makeAuthentication() },
                        onFailure = { error -> throw error }
                    )
                }
            }
        }
    }
}