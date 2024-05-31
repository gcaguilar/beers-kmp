package presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import domain.Beer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import presentation.ui.BeerItem

const val InfiniteStagedGridListTag = "InfiniteStagedGridList"

@Composable
fun InfiniteStagedGridList(
    scrollingDown: Boolean,
    beerList: List<Beer>,
    buffer: Int = 5,
    canRequestMoreData: Boolean,
    isRequestingMoreItems: Boolean,
    onClick: (bid: String) -> Unit,
    onLoadMore: () -> Unit,
    listState: LazyStaggeredGridState,
    modifier: Modifier = Modifier,
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier.fillMaxSize()
            .testTag(InfiniteStagedGridListTag),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp,
        state = listState,
        columns = StaggeredGridCells.Adaptive(136.dp)
    ) {
        items(
            count = beerList.size,
//            key = { index -> beerList[index].bid }
        ) { index ->
            Card {
                with(beerList[index]) {
                    BeerItem(
                        image = image,
                        name = name,
                        style = style,
                        abv = abv.toString(),
                        ibu = ibu.toString(),
                        showRate = true,
                        onClick = { onClick(bid.toString()) }
                    )
                }
            }
        }
    }

    if (scrollingDown && !isRequestingMoreItems) {
        LaunchedEffect(listState) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0 }
                .map { lastVisibleItem ->
                    if (listState.layoutInfo.totalItemsCount > 0) {
                        lastVisibleItem >= listState.layoutInfo.totalItemsCount - buffer
                    } else {
                        false
                    }
                }
                .collectLatest { requestMore ->
                    if (requestMore && canRequestMoreData) {
                        onLoadMore()
                    }
                }
        }
    }
}

@Composable
internal fun LazyStaggeredGridState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    val scrollOffsetThreshold = 10.dp // Adjust as needed
    val scrollOffsetThresholdInPx = with(LocalDensity.current) { scrollOffsetThreshold.toPx() }

    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                firstVisibleItemScrollOffset - previousScrollOffset >= scrollOffsetThresholdInPx
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}
