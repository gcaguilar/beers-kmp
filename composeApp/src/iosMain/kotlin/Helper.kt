import di.networkModule
import di.dataModule
import di.domainModule
import di.presentationModule
import di.platform
import org.koin.core.context.startKoin

fun initKoin() =
    startKoin {
        modules(networkModule + dataModule + domainModule + presentationModule + platform)
    }
