package org.gcaguilar.kmmbeers.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import kmmbeers.composeapp.generated.resources.Res
import kmmbeers.composeapp.generated.resources.splash
import org.gcaguilar.kmmbeers.presentation.Screen
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.koinInject
import org.gcaguilar.kmmbeers.presentation.authentication.LoginScreen
import org.gcaguilar.kmmbeers.presentation.search.SearchScreen
import org.koin.compose.viewmodel.koinViewModel
import co.touchlab.kermit.Logger

private const val IsLoggedKey = "IsLoggedKey"

@Composable
fun Splash(
    viewModel: SplashViewModel = koinViewModel<SplashViewModel>(),
    navigate: (String) -> Unit,
) {
    val state = viewModel.state.collectAsState().value

    Image(
        modifier = Modifier.fillMaxSize(),
        imageVector = vectorResource(Res.drawable.splash),
        contentDescription = "Splash org.gcaguilar.kmmbeers.Screen",
    )

    state.isLoggedIn?.let { isLoggedIn ->
        val destination = if (isLoggedIn) {
            Screen.Search.route
        } else {
            Screen.Authentitcation.route
        }
       navigate(destination)
    }

    LaunchedEffect(IsLoggedKey) {
        viewModel.isLogged()
    }
}