package presentation.beer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import presentation.beer.BeerDetailScreenModel.UIState
import presentation.brewery.BreweryDetail
import presentation.ui.HeadingTextBlock
import presentation.ui.InfoSection
import presentation.ui.Rating

private const val FetchBeerKey = "Beer"

data class DetailScreen(val bid: String) : Screen {
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<BeerDetailScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        when (state) {
            UIState.Error -> Text("Error")
            UIState.Loading -> CircularProgressIndicator()
            is UIState.Success -> Detail(
                descriptionSection = {
                    InfoSection(
                        imageUrl = (state as UIState.Success).beerDetail.image,
                        name = (state as UIState.Success).beerDetail.name,
                        description = (state as UIState.Success).beerDetail.description
                    )
                },
                propertiesSection = PropertiesSection(
                    ibu = (state as UIState.Success).beerDetail.ibu,
                    abv = (state as UIState.Success).beerDetail.abv,
                    style = (state as UIState.Success).beerDetail.style
                ),
                brewerSection = BrewerSection(
                    brewerName = (state as UIState.Success).beerDetail.brewery.name,
                    onClick = {
                        navigator.push(BreweryDetail(id = (state as UIState.Success).beerDetail.brewery.id.toString()))
                    }
                ),
                ratingSection = RatingSection()
            )
        }

        LaunchedEffect(key1 = FetchBeerKey) {
            screenModel.getBeer(bid)
        }
    }

    @Composable
    private fun Detail(
        descriptionSection: @Composable () -> Unit,
        propertiesSection: @Composable ColumnScope.() -> Unit,
        brewerSection: @Composable ColumnScope.() -> Unit,
        ratingSection: @Composable ColumnScope.() -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            descriptionSection()
            propertiesSection()
            brewerSection()
            ratingSection()
        }
    }
}

@Composable
private fun RatingSection(): @Composable (ColumnScope.() -> Unit) = {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Ratings & Reviews",
        style = MaterialTheme.typography.headlineSmall,
    )
    Rating()
}

@Composable
private fun PropertiesSection(
    ibu: String,
    style: String,
    abv: String,
): @Composable (ColumnScope.() -> Unit) = {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        HeadingTextBlock(
            title = "Type",
            subtitle = style
        )
        HeadingTextBlock(
            title = "ABV",
            subtitle = abv
        )
        HeadingTextBlock(
            title = "IBU",
            subtitle = ibu
        )
    }
}

@Composable
private fun BrewerSection(
    brewerName: String,
    location: String = "Paso Robles, CA",
    onClick: () -> Unit
): @Composable (ColumnScope.() -> Unit) = {
    Column(
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Brewer",
            style = MaterialTheme.typography.headlineSmall,
        )
        HeadingTextBlock(
            title = "Name",
            subtitle = brewerName
        )
        HeadingTextBlock(
            title = "Location",
            subtitle = location
        )
    }
}
