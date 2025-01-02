package org.gcaguilar.kmmbeers.presentation.brewery

import androidx.compose.foundation.layout.*
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
import org.gcaguilar.kmmbeers.domain.Beer
import org.gcaguilar.kmmbeers.presentation.ui.BeerItem
import org.gcaguilar.kmmbeers.presentation.ui.InfoSection
import org.koin.compose.viewmodel.koinViewModel

private const val FetchBreweryKey = "FetchBrewery"

@Composable

fun BreweryDetail(id: String, navigate: (String) -> Unit) {
    val viewModel = koinViewModel<BreweryDetailViewModel>()
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
    ) {
        when (state) {
            BreweryDetailViewModel.UIState.Error -> Text("Error")
            BreweryDetailViewModel.UIState.Loading -> CircularProgressIndicator()
            is BreweryDetailViewModel.UIState.Success -> BreweryDetail(
                infoSection = {
                    InfoSection(
                        imageUrl = (state as BreweryDetailViewModel.UIState.Success).breweryDetail.imageUrl,
                        name = (state as BreweryDetailViewModel.UIState.Success).breweryDetail.name,
                        description = (state as BreweryDetailViewModel.UIState.Success).breweryDetail.description
                            ?: "",
                    )
                },
                locationSection = {},
                socialMedia = {},
                beerList = {}
            )
        }
    }

    LaunchedEffect(key1 = FetchBreweryKey) {
        viewModel.fetchBreweryDetail(id)
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

