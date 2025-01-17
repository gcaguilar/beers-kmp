package org.gcaguilar.kmmbeers.presentation.beer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import org.gcaguilar.kmmbeers.presentation.Screen
import org.gcaguilar.kmmbeers.presentation.ui.HeadingTextBlock
import org.gcaguilar.kmmbeers.presentation.ui.InfoSection
import org.gcaguilar.kmmbeers.presentation.ui.Rating
import org.koin.compose.viewmodel.koinViewModel

private const val FetchBeerKey = "Beer"

@Composable
fun BeerDetailScreen(
    bid: String,
    navigate: (String) -> Unit
) {
    val viewModel = koinViewModel<BeerDetailViewModel>()
    val state by viewModel.state.collectAsState()

    when (state) {
        BeerDetailViewModel.UIState.Error -> Text("Error")
        BeerDetailViewModel.UIState.Loading -> CircularProgressIndicator()
        is BeerDetailViewModel.UIState.Success -> Detail(
            descriptionSection = {
                InfoSection(
                    imageUrl = (state as BeerDetailViewModel.UIState.Success).beerDetail.image,
                    name = (state as BeerDetailViewModel.UIState.Success).beerDetail.name,
                    description = (state as BeerDetailViewModel.UIState.Success).beerDetail.description
                )
            },
            propertiesSection = PropertiesSection(
                ibu = (state as BeerDetailViewModel.UIState.Success).beerDetail.ibu,
                abv = (state as BeerDetailViewModel.UIState.Success).beerDetail.abv,
                style = (state as BeerDetailViewModel.UIState.Success).beerDetail.style
            ),
            brewerSection = BrewerSection(
                brewerName = (state as BeerDetailViewModel.UIState.Success).beerDetail.brewery.name,
                onClick = {
                    navigate(
                        Screen.BreweryDetail.createRoute((state as BeerDetailViewModel.UIState.Success).beerDetail.brewery.id.toString())
                    )
                }
            ),
            ratingSection = RatingSection()
        )
    }

    LaunchedEffect(key1 = FetchBeerKey) {
        viewModel.getBeer(bid)
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
