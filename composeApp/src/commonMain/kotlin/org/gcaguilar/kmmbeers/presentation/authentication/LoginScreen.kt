package org.gcaguilar.kmmbeers.presentation.authentication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.gcaguilar.kmmbeers.presentation.search.SearchScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

private const val FetchAuthenticationUrlKey = "FetchUri"

object LoginScreen : Screen {
    @OptIn(KoinExperimentalAPI::class)
    @Composable
    override fun Content() {
        val screenModel = koinViewModel<LoginScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            screenModel.checkStatus()
        }

        state.events?.let {
            navigator.replace(SearchScreen)
            screenModel.processNavigation()
        }
    }
}