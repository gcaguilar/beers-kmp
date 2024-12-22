package org.gcaguilar.kmmbeers.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import kmmbeers.composeapp.generated.resources.Res
import kmmbeers.composeapp.generated.resources.splash
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.koinInject
import org.gcaguilar.kmmbeers.presentation.authentication.LoginScreen
import org.gcaguilar.kmmbeers.presentation.search.SearchScreen
import org.koin.compose.viewmodel.koinViewModel

private const val IsLoggedKey = "IsLoggedKey"

@Composable
fun SplashContent(
) {
    val viewModel = koinViewModel<SplashScreenModel>()
    val state = viewModel.state.collectAsState().value

    Image(
        modifier = Modifier.fillMaxSize(),
        imageVector = vectorResource(Res.drawable.splash),
        contentDescription = "Splash Screen",
    )

    state.isLoggedIn?.let { isLoggedIn ->
        val destination = if (isLoggedIn) {
            //SearchScreen
        } else {
           // LoginScreen
        }
        // navigator.replace(destination)
    }

    LaunchedEffect(IsLoggedKey) {
        viewModel.isLogged()
    }
}