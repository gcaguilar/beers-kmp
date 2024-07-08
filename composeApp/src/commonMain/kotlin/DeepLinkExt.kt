import cafe.adriel.voyager.core.screen.Screen
import dev.theolm.rinku.DeepLink
import io.github.aakira.napier.Napier
import presentation.authentication.AuthenticationScreen
import presentation.splash.SplashScreen


@Suppress("TooGenericExceptionCaught", "SwallowedException")
fun DeepLink?.toScreenStack(): List<Screen> {
    if (this == null) {
        return listOf(SplashScreen)
    }

    val parameters = try {
        this.parameters["code"]
    } catch (e: Exception) {
        Napier.d("No code parameter found")
        null
    }

    return listOf(AuthenticationScreen(parameters))
}