package presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.beer.DetailScreen
import presentation.ui.Search

object SearchScreen : Screen {
    @OptIn(KoinExperimentalAPI::class)
    @Composable
    override fun Content() {
        val screenModel = koinViewModel<SearchScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val listState = rememberLazyStaggeredGridState()

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchResultContent(
                topSection = {
                    Search(
                        modifier = Modifier,
                        searchInput = state.searchText ?: "",
                        onSearchInputChanged = {
                            screenModel.onInputTextChange(it)
                        },
                        onClearTapped = {
                            screenModel.onClearInputText()
                        },
                        placeHolder = "Search",
                        submitSearch = {
                            screenModel.searchBeer()
                        }
                    )
                },
                resultContent = {
                    when {
                        state.isLoading -> CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )

                        state.error != null -> Text(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.CenterHorizontally),
                            text = "Error"
                        )

                        state.searchResult?.isEmpty() == true -> Text("Empty")
                        else -> {
                            Column {
                                InfiniteStagedGridList(
                                    modifier = Modifier
                                        .padding(vertical = 16.dp)
                                        .weight(1f),
                                    beerList = state.searchResult ?: emptyList(),
                                    listState = listState,
                                    canRequestMoreData = state.nextPage?.let { true } ?: false,
                                    isRequestingMoreItems = state.isRequestingMoreItems,
                                    scrollingDown = !listState.isScrollingUp(),
                                    onClick = {
                                        navigator.push(DetailScreen(it))
                                    },
                                    onLoadMore = {
                                        screenModel.requestNextPage()
                                    }
                                )
                                AnimatedVisibility(state.isRequestingMoreItems) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun SearchResultContent(
    topSection: @Composable ColumnScope.() -> Unit,
    resultContent: @Composable ColumnScope.() -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        topSection()
        resultContent()
    }
}
