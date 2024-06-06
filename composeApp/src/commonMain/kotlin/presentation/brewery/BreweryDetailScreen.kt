package presentation.brewery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.Beer
import presentation.ui.BeerItem
import presentation.ui.InfoSection

private const val FetchBreweryKey = "FetchBrewery"

data class BreweryDetail(val id: String) : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<BreweryDetailScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {
            when (state) {
                BreweryDetailScreenModel.UIState.Error -> Text("Error")
                BreweryDetailScreenModel.UIState.Loading -> CircularProgressIndicator()
                is BreweryDetailScreenModel.UIState.Success -> BreweryDetail(
                    infoSection = {
                        InfoSection(
                            imageUrl = (state as BreweryDetailScreenModel.UIState.Success).breweryDetail.imageUrl,
                            name = (state as BreweryDetailScreenModel.UIState.Success).breweryDetail.name,
                            description = (state as BreweryDetailScreenModel.UIState.Success).breweryDetail.description ?: "",
                        )
                    },
                    locationSection = {},
                    socialMedia = {},
                    beerList = {}
                )
            }
        }

        LaunchedEffect(key1 = FetchBreweryKey) {
            screenModel.fetchBreweryDetail(id)
        }
    }
}

@Composable
private fun BreweryDetail(
    infoSection: @Composable () -> Unit,
    locationSection: @Composable ColumnScope.() -> Unit,
    socialMedia: @Composable ColumnScope.() -> Unit,
    beerList: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        infoSection()
        locationSection()
        socialMedia()
        beerList()
    }
}

@Composable
private fun beerRowList(
    beerList: List<Beer>,
    onClick: (bid: Int) -> Unit
) {
    LazyRow {
        items(beerList.size) { index ->
            with(beerList[index]) {
                BeerItem(
                    image = image,
                    name = name,
                    style = style,
                    abv = abv.toString(),
                    ibu = ibu.toString(),
                    showRate = false,
                    onClick = { onClick(bid) }
                )
            }
        }
    }
}

