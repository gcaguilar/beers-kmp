package presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import domain.Beer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import presentation.ui.BeerItem

const val InfiniteStagedGridListTag = "InfiniteStagedGridList"

@Composable
fun InfiniteStagedGridList(
    lastScrollPosition: Int,
    beerList: List<Beer>,
    buffer: Int = 5,
    canRequestMoreData: Boolean = false,
    isRequestingMoreItems: Boolean = false,
    onClick: (bid: String) -> Unit,
    onLoadMore: () -> Unit,
    updateLastScrollPosition: (Int) -> Unit,
    listState: LazyStaggeredGridState,
    modifier: Modifier = Modifier
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
            key = { index -> beerList[index].bid }
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

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .map { visibleItems ->
                val lastVisibleItem = visibleItems.lastOrNull()
                val atEnd = (lastVisibleItem?.index ?: 0) >= listState.layoutInfo.totalItemsCount - buffer
                val scrollingDown = listState.firstVisibleItemIndex > lastScrollPosition
                Pair(atEnd, scrollingDown)
            }
            .distinctUntilChanged()
            .collectLatest { (atEnd, scrollingDown) ->
                if (atEnd && scrollingDown && canRequestMoreData && !isRequestingMoreItems) {
                    onLoadMore()
                }
                updateLastScrollPosition(listState.firstVisibleItemIndex)
            }
    }
}
