package presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.ui.Rating

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
    when (val state = detailViewModel.uiState.collectAsState().value) {
        UIState.Error -> Text("Error")
        UIState.Loading -> CircularProgressIndicator()
        is UIState.Success -> Detail(
            imageUrl = state.beerDetail.image,
            name = state.beerDetail.name,
            description = state.beerDetail.description,
            abv = state.beerDetail.abv,
            ibu = state.beerDetail.ibu,
            style = state.beerDetail.style,
            brewerName = state.beerDetail.brewery.name
        )
    }

    LaunchedEffect(key1 = "Beer") {
        detailViewModel.getBeer(bid)
    }
}

@Composable
fun Detail(
    name: String,
    description: String,
    style: String,
    abv: String,
    ibu: String,
    imageUrl: String,
    brewerName: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        KamelImage(
            modifier = Modifier.fillMaxWidth()
                .height(100.dp),
            resource = asyncPainterResource(imageUrl),
            contentDescription = "",
            contentScale = ContentScale.Fit
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = name,
            style = MaterialTheme.typography.headlineSmall,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = description,
            style = MaterialTheme.typography.bodyMedium,

            )
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
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Brewer",
            style = MaterialTheme.typography.headlineSmall,
        )
        HeadingTextBlock(
            title = "Name",
            subtitle = brewerName,
        )
        HeadingTextBlock(
            title = "Location",
            subtitle = "Paso Robles, CA"
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Ratings & Reviews",
            style = MaterialTheme.typography.headlineSmall,
        )
        Rating()
    }

}
