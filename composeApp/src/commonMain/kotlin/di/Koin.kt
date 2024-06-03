package di


import data.AuthenticationRepositoryImpl
import data.AuthenticationService
import data.SearchRepositoryImpl
import data.SecureStorage
import data.UntappdService
import domain.Authenticate
import domain.AuthenticationRepository
import domain.GetAuthenticationUrl
import domain.GetBeerDetail
import domain.GetBreweryDetail
import domain.IsLoggedIn
import domain.SearchBeer
import domain.SearchRepository
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.github.alexandereggers.ksecurestorage.KSecureStorage
import io.ktor.client.HttpClient
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.authentication.LoginViewModel
import presentation.beer.DetailViewModel
import presentation.brewery.BreweryDetailViewModel
import presentation.search.SearchViewModel
import presentation.splash.SplashViewModel

fun initKoin() =
    startKoin {
        modules(networkModule + dataModule + domainModule + presentationModule)
    }

private val presentationModule = module {
    viewModelOf(::SearchViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::BreweryDetailViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::SplashViewModel)

}

private val domainModule = module {
    factory {
        SearchBeer(
            searchRepository = get()
        )
    }

    factory {
        GetBeerDetail(
            searchRepository = get()
        )
    }
    factory {
        GetBreweryDetail(
            searchRepository = get()
        )
    }
    factory {
        GetAuthenticationUrl(
            authenticationRepository = get()
        )
    }
    factory {
        Authenticate(
            authenticationRepository = get()
        )
    }
    factory {
        IsLoggedIn(
            authenticationRepository = get()
        )
    }
}

private val dataModule = module {
    single { KSecureStorage() }
    factory {
        SecureStorage(
            kSecureStorage = get()
        )
    }
    factory<SearchRepository> {
        SearchRepositoryImpl(
            service = get()
        )
    }
    factory<AuthenticationRepository> {
        AuthenticationRepositoryImpl(
            authenticationService = get(),
            secureStorage = get()
        )
    }
    factory<AuthenticationRepository> {
        AuthenticationRepositoryImpl(
            authenticationService = get(),
            secureStorage = get()
        )
    }
    single {
        UntappdService(
            ktorClient = get(),
            secureStorage = get()
        )
    }
    single {
        AuthenticationService(
            ktorClient = get()
        )
    }
}

private val networkModule = module {
    single {
        HttpClient {
            install(UserAgent) {
                agent = "BeersKMM D912C0B80A28A04F4EA2FD48E625853326BEAB1C"
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.d("HTTP Client", null, message)
                    }
                }
                level = LogLevel.ALL
            }
        }.also { Napier.base(DebugAntilog()) }
    }
}
