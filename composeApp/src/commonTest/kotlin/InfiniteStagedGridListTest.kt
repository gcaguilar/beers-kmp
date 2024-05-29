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
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalTestApi
class InfiniteStagedGridListTest {
    @Test
    fun `Should call onLoadMore when scroll to item out of buffer`() = runComposeUiTest {
        val beerList = DummyBeer.generateFakeBeers(150)
        val onLoadMoreCalled = mutableStateOf(false)
        val onLoadMore: () -> Unit = { onLoadMoreCalled.value = true }
        var lastScrollPosition = 0
        val updateLastScrollPosition: (Int) -> Unit = { position -> lastScrollPosition = position }

        setContent {
            Box(Modifier.size(400.dp, 800.dp)) {
                InfiniteStagedGridList(
                    lastScrollPosition = lastScrollPosition,
                    beerList = beerList,
                    canRequestMoreData = true,
                    isRequestingMoreItems = false,
                    onClick = { /*...*/ },
                    onLoadMore = onLoadMore,
                    updateLastScrollPosition = updateLastScrollPosition,
                    listState = rememberLazyStaggeredGridState(),
                    modifier = Modifier
                )
            }
        }

        onNodeWithTag(InfiniteStagedGridListTag).performScrollToIndex(149)

        assertTrue(onLoadMoreCalled.value)
    }

    @Test
    fun `Should not call onLoadMore when scroll to item inside buffer`() = runComposeUiTest {
        val beerList = DummyBeer.generateFakeBeers(150)
        val onLoadMoreCalled = mutableStateOf(false)
        val onLoadMore: () -> Unit = { onLoadMoreCalled.value = true }
        var lastScrollPosition = 0
        val updateLastScrollPosition: (Int) -> Unit = { position -> lastScrollPosition = position }

        setContent {
            Box(Modifier.size(400.dp, 800.dp)) {
                InfiniteStagedGridList(
                    lastScrollPosition = lastScrollPosition,
                    beerList = beerList,
                    canRequestMoreData = true,
                    isRequestingMoreItems = false,
                    onClick = { /*...*/ },
                    onLoadMore = onLoadMore,
                    updateLastScrollPosition = updateLastScrollPosition,
                    listState = rememberLazyStaggeredGridState(),
                    modifier = Modifier
                )
            }
        }

        onNodeWithTag(InfiniteStagedGridListTag).performScrollToIndex(130)

        assertFalse(onLoadMoreCalled.value)
    }
}
