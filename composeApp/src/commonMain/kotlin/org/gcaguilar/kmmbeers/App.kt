import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.gcaguilar.kmmbeers.presentation.authentication.LoginScreen

@Composable
fun App() {
    // ProvideNavigatorLifecycleKMPSupport {
        MainScreen()
    //}
}

@Composable
fun MainScreen() {
    MaterialTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
//            topBar = {
//                CustomAppBar(
//                    navigationIcon = {
//                        if (navigator.lastItem::class.simpleName != SearchScreen::class.simpleName) {
//                            BackNavigationIcon(onClickIconButton = { navigator.pop() })
//                        }
//                    }
//                )
//            },
            content = { _ ->
                LoginScreen()
            }
        )
    }
}
