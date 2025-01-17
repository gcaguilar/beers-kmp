import org.gcaguilar.kmmbeers.di.dataModule
import org.gcaguilar.kmmbeers.di.domainModule
import org.gcaguilar.kmmbeers.di.presentationModule
import org.gcaguilar.kmmbeers.di.platform
import org.koin.core.context.startKoin

fun initKoin() =
    startKoin {
        modules(dataModule + domainModule + presentationModule + platform)
    }
