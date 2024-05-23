package presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import presentation.detail.DetailScreen

object SearchScreen: Screen, KoinComponent {
    @Composable
    override fun Content() {
        SearchScreenContent()
    }
}

private const val SearchKey = "Search"

@Composable
fun SearchScreenContent(viewModel: SearchViewModel = koinInject<SearchViewModel>()) {
    val state = viewModel.uiState.collectAsState().value
    val navigator = LocalNavigator.currentOrThrow
    
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is UIState.Error -> Text("Error")
            UIState.Loading -> CircularProgressIndicator()
            is UIState.Success -> {
                ResultList(
                    beerList = state.beers,
                    onClick = {
                        navigator.push(DetailScreen(it))
                    }
                )
            }
        }
    }

    LaunchedEffect(key1 = SearchKey) {
        viewModel.searchBeer()
    }
}