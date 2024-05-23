package presentation.detail

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import presentation.brewery.BreweryDetail
import presentation.ui.HeadingTextBlock
import presentation.ui.InfoSection
import presentation.ui.Rating

private const val FetchBeerKey = "Beer"

data class DetailScreen(val bid: String) : Screen, KoinComponent {
    @Composable
    override fun Content() {
        DetailContent(bid)
    }
}

@Composable
fun DetailContent(
    bid: String,
    detailViewModel: DetailViewModel = koinInject<DetailViewModel>()
) {
    val navigator = LocalNavigator.currentOrThrow

    when (val state = detailViewModel.uiState.collectAsState().value) {
        UIState.Error -> Text("Error")
        UIState.Loading -> CircularProgressIndicator()
        is UIState.Success -> Detail(
            descriptionSection = {
                InfoSection(
                    imageUrl = state.beerDetail.image,
                    name = state.beerDetail.name,
                    description = state.beerDetail.description
                )
            },
            propertiesSection = PropertiesSection(
                ibu = state.beerDetail.ibu,
                abv = state.beerDetail.abv,
                style = state.beerDetail.style
            ),
            brewerSection = BrewerSection(
                brewerName = state.beerDetail.brewery.name,
                onClick = {
                    navigator.push(BreweryDetail(id = state.beerDetail.brewery.id.toString()))
                }
            ),
            ratingSection = RatingSection()
        )
    }

    LaunchedEffect(key1 = FetchBeerKey) {
        detailViewModel.getBeer(bid)
    }
}

@Composable
fun Detail(
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