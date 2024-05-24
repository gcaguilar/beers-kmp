package presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import domain.Beer
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun ResultList(
    beerList: List<Beer>,
    modifier: Modifier = Modifier,
    onClick: (bid: String) -> Unit
) {
    
    LazyVerticalStaggeredGrid(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp,
        columns = StaggeredGridCells.Adaptive(136.dp)
    ) {
        items(beerList.size) {
            Card {
                ResultItem(
                    beer = beerList[it],
                    onClick = onClick
                )
            }
        }
    }
}


@Composable
fun ResultItem(
    beer: Beer,
    onClick: (bid: String) -> Unit,
    ) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth(),
        onClick = { onClick(beer.bid.toString()) } 
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
            KamelImage(
                resource = asyncPainterResource(beer.image),
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
                    .height(100.dp)
                    .fillMaxWidth(),
                contentDescription = "data.Beer image",
                contentScale = ContentScale.Fit
            )
            Text(
                text = beer.name,
                style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = beer.style,
                    style = MaterialTheme.typography.bodySmall
                )
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "${beer.abv} - ${beer.ibu} IBU",
                        style = MaterialTheme.typography.bodySmall,
                        )
                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.Start)
                ) {
                    Text(text = "Rate")
                }
        }
    }
}
