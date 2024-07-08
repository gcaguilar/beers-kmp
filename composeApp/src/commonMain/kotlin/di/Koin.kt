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
import io.github.alexandereggers.ksecurestorage.KSecureStorage
import io.ktor.client.HttpClient
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import presentation.authentication.LoginScreenModel
import presentation.beer.BeerDetailScreenModel
import presentation.brewery.BreweryDetailScreenModel
import presentation.search.SearchScreenModel
import presentation.splash.SplashScreenModel

fun initKoin() =
    startKoin {
        modules(networkModule + dataModule + domainModule + presentationModule)
    }

 val presentationModule = module {
    factory { SearchScreenModel(searchBeer = get()) }
    factory { BeerDetailScreenModel(getBeerDetail = get()) }
    factory { BreweryDetailScreenModel(getBreweryDetail = get()) }
    factory { LoginScreenModel(authenticate = get(), getAuthenticationUrl = get()) }
    factory { SplashScreenModel(isLoggedIn = get()) }
}

 val domainModule = module {
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

 val dataModule = module {
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

 val networkModule = module {
    single(named("UserAgent")) { "BeersKMM D912C0B80A28A04F4EA2FD48E625853326BEAB1C" }

    single {
        HttpClient {
            install(UserAgent) {
                agent = get(named(("UserAgent")))
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Logging)
        }
    }
}
