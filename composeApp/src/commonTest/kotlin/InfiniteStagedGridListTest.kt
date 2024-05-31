import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import presentation.search.InfiniteStagedGridList
import presentation.search.InfiniteStagedGridListTag
import presentation.search.isScrollingUp
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private const val StandardSizeList = 150
private const val FirstScrollOutOfBuffer = 149

@ExperimentalTestApi
class InfiniteStagedGridListTest {
    @Test
    fun `Should call onLoadMore when scroll to item out of buffer`() = runComposeUiTest {
        val beerList = DummyBeer.generateFakeBeers(StandardSizeList)
        var onLoadMoreCalled = false
        val onLoadMore: () -> Unit = { onLoadMoreCalled = true }

        setContent {
            val listState = rememberLazyStaggeredGridState()

            Box(Modifier.size(400.dp, 800.dp)) {
                InfiniteStagedGridList(
                    beerList = beerList,
                    canRequestMoreData = true,
                    isRequestingMoreItems = false,
                    onClick = { /*...*/ },
                    onLoadMore = onLoadMore,
                    listState = listState,
                    scrollingDown = !listState.isScrollingUp(),
                    modifier = Modifier
                )
            }
        }

        onNodeWithTag(InfiniteStagedGridListTag).performScrollToIndex(FirstScrollOutOfBuffer)

        assertTrue(onLoadMoreCalled)
    }

    @Test
    fun `Should not call onLoadMore when scroll to item inside buffer`() = runComposeUiTest {
        val beerList = DummyBeer.generateFakeBeers(StandardSizeList).toMutableList()
        val onLoadMore: () -> Unit = { beerList.addAll(beerList) }
        val scrollInsideBuffer = 100

        setContent {
            val listState = rememberLazyStaggeredGridState()

            Box(Modifier.size(400.dp, 800.dp)) {
                InfiniteStagedGridList(
                    beerList = beerList,
                    canRequestMoreData = true,
                    isRequestingMoreItems = false,
                    onClick = { /*...*/ },
                    onLoadMore = onLoadMore,
                    listState = listState,
                    scrollingDown = !listState.isScrollingUp(),
                    modifier = Modifier
                )
            }
        }

        onNodeWithTag(InfiniteStagedGridListTag).performScrollToIndex(scrollInsideBuffer)

        assertEquals(StandardSizeList, beerList.size)
    }

    @Test
    fun `Should call onLoadMore when scroll to item out of buffer after data increase`() = runComposeUiTest {
        val scrollOutOfBuffer = 299
        val tripleStandardListSize = StandardSizeList * 3
        val beerList = DummyBeer.generateFakeBeers(StandardSizeList).toMutableList()
        val beerListState = mutableStateOf(beerList)
        val additionalItems = DummyBeer.generateFakeBeers(StandardSizeList)
        val onLoadMore: () -> Unit = {
            beerListState.value = beerList.apply { addAll(additionalItems) }
        }

        setContent {
            val listState = rememberLazyStaggeredGridState()

            Box(Modifier.size(400.dp, 800.dp)) {
                InfiniteStagedGridList(
                    beerList = beerList,
                    canRequestMoreData = true,
                    isRequestingMoreItems = false,
                    onClick = { /*...*/ },
                    onLoadMore = onLoadMore,
                    listState = listState,
                    scrollingDown = !listState.isScrollingUp(),
                    modifier = Modifier
                )
            }
        }

        onNodeWithTag(InfiniteStagedGridListTag).performScrollToIndex(FirstScrollOutOfBuffer)
        onNodeWithTag(InfiniteStagedGridListTag).performScrollToIndex(scrollOutOfBuffer)

        assertEquals(tripleStandardListSize, beerList.size)
    }

    @Test
    fun `Should not call onLoadMore when scroll to item out of buffer after data increase`() = runComposeUiTest {
        val scrollOutOfBuffer = 250
        val scrollInsideBuffer = 270
        val doubleStandardListSize = StandardSizeList * 2
        val beerList = DummyBeer.generateFakeBeers(StandardSizeList).toMutableList()
        val additionalItems = DummyBeer.generateFakeBeers(StandardSizeList)
        val moreItems = DummyBeer.generateFakeBeers(StandardSizeList)
        var onLoadMoreCalled = false
        val onLoadMore: () -> Unit = {
            if (!onLoadMoreCalled) {
                beerList += (additionalItems)
                onLoadMoreCalled = true
            } else {
                beerList += (moreItems)
            }
        }

        setContent {
            val listState = rememberLazyStaggeredGridState()

            Box(Modifier.size(400.dp, 800.dp)) {
                InfiniteStagedGridList(
                    beerList = beerList,
                    canRequestMoreData = true,
                    isRequestingMoreItems = false,
                    onClick = { /*...*/ },
                    onLoadMore = onLoadMore,
                    listState = listState,
                    scrollingDown = !listState.isScrollingUp(),
                    modifier = Modifier
                )
            }
        }

        onNodeWithTag(InfiniteStagedGridListTag).performScrollToIndex(FirstScrollOutOfBuffer)
        onNodeWithTag(InfiniteStagedGridListTag).performScrollToIndex(scrollOutOfBuffer)
        onNodeWithTag(InfiniteStagedGridListTag).performScrollToIndex(scrollInsideBuffer)

        assertEquals(doubleStandardListSize, beerList.size)
    }
}
