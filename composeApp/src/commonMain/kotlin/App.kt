import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.jetpack.ProvideNavigatorLifecycleKMPSupport
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import dev.theolm.rinku.DeepLink
import dev.theolm.rinku.compose.ext.DeepLinkListener
import di.*
import org.koin.compose.KoinApplication
import presentation.splash.SplashScreen

@OptIn(ExperimentalVoyagerApi::class)
@Composable
fun App() {
    var deepLink by remember { mutableStateOf<DeepLink?>(null) }
    DeepLinkListener { deepLink = it }
    ProvideNavigatorLifecycleKMPSupport {
        MainScreen(deepLink)
    }
}

@Composable
fun MainScreen(deepLink: DeepLink?) {
//    val screenStack: List<Screen> = remember(deepLink) {
//        deepLink.toScreenStack()
//    }

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
                Navigator(screen = SplashScreen) { navigator ->
                    Box(Modifier.padding(paddingValues)) {
                        CurrentScreen()
//                        remember(screenStack) {
//                            navigator.replaceAll(screenStack)
//                        }
                    }
                }
            }
        )
    }
}
