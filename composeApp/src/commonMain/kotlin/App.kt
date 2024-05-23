import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.search.SearchScreen
import presentation.ui.BackNavigationIcon
import presentation.ui.CustomAppBar

@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigator(SearchScreen) { navigator ->
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    CustomAppBar(
                        navigationIcon = {
                          if (navigator.lastItem::class.simpleName != SearchScreen::class.simpleName) {
                               BackNavigationIcon(onClickIconButton = { navigator.pop() })
                          }
                      }
                  )
                },
                content = {
                    Box(Modifier.padding(it)) {
                        CurrentScreen()
                    }
                }
            )
        }
    }
}
