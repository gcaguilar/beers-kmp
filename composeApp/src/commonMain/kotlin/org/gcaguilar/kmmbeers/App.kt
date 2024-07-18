import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.jetpack.ProvideNavigatorLifecycleKMPSupport
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import org.gcaguilar.kmmbeers.presentation.splash.SplashScreen

@OptIn(ExperimentalVoyagerApi::class)
@Composable
fun App() {
    ProvideNavigatorLifecycleKMPSupport {
        MainScreen()
    }
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
            content = { paddingValues ->
                Navigator(screen = SplashScreen) {
                    Box(Modifier.padding(paddingValues)) {
                        CurrentScreen()
                    }
                }
            }
        )
    }
}
