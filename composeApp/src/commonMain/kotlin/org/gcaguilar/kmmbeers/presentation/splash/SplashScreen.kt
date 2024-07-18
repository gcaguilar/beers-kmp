package org.gcaguilar.kmmbeers.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kmmbeers.composeapp.generated.resources.Res
import kmmbeers.composeapp.generated.resources.splash
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.koinInject
import org.gcaguilar.kmmbeers.presentation.authentication.LoginScreen
import org.gcaguilar.kmmbeers.presentation.search.SearchScreen

object SplashScreen : Screen {
    @Composable
    override fun Content() {
        SplashContent()
    }
}

private const val IsLoggedKey = "IsLoggedKey"

@Composable
fun SplashContent(
    ScreenModel: SplashScreenModel = koinInject()
) {
    val state = ScreenModel.state.collectAsState().value
    val navigator = LocalNavigator.currentOrThrow

    Image(
        modifier = Modifier.fillMaxSize(),
        imageVector = vectorResource(Res.drawable.splash),
        contentDescription = "Splash Screen",
    )

    state.isLoggedIn?.let { isLoggedIn ->
        val destination = if (isLoggedIn) {
            SearchScreen
        } else {
           LoginScreen
        }
        navigator.replace(destination)
    }

    LaunchedEffect(IsLoggedKey) {
        ScreenModel.isLogged()
    }
}