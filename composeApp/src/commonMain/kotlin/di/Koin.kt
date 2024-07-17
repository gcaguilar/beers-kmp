package di

import data.*
import domain.*
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.publicvalue.multiplatform.oidc.OpenIdConnectClient
import org.publicvalue.multiplatform.oidc.types.CodeChallengeMethod
import presentation.authentication.LoginScreenModel
import presentation.beer.BeerDetailScreenModel
import presentation.brewery.BreweryDetailScreenModel
import presentation.search.SearchScreenModel
import presentation.splash.SplashScreenModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.publicvalue.multiplatform.oidc.ExperimentalOpenIdConnect
import org.publicvalue.multiplatform.oidc.tokenstore.SettingsStore
import org.publicvalue.multiplatform.oidc.tokenstore.SettingsTokenStore
import org.publicvalue.multiplatform.oidc.tokenstore.TokenStore

val presentationModule = module {
    viewModelOf(::SearchScreenModel)
    factory { BeerDetailScreenModel(getBeerDetail = get()) }
    factory { BreweryDetailScreenModel(getBreweryDetail = get()) }
    viewModelOf(::LoginScreenModel)
    viewModelOf(::SplashScreenModel)
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
        IsLoggedIn(
            authenticationRepository = get()
        )
    }
    factory {
        Authenticate(
            authenticationRepository = get()
        )
    }
}

@OptIn(ExperimentalOpenIdConnect::class)
val dataModule = module {
    single{
        OpenIdConnectClient {
            endpoints {
                tokenEndpoint = "https://untappd.com/oauth/authorize/"
                authorizationEndpoint = "https://untappd.com/oauth/authenticate/"
                userInfoEndpoint = null
                endSessionEndpoint = "<endSessionEndpoint>"
            }

            clientId = ""
            clientSecret = ""
            scope = null
            codeChallengeMethod = CodeChallengeMethod.off
            redirectUri = "org.gcaguilar.kmmbeers://auth"
        }
    }

    factory<SearchRepository> {
        SearchRepositoryImpl(
            service = get()
        )
    }
    factory<AuthenticatorService>{
        AuthenticationServiceImpl(
            client = get(),
            codeAuthFlowFactory = get()
       )
    }
    factory<AuthenticationRepository> {
        AuthenticationRepositoryImpl(
            authenticatorService = get(),
            tokenStore = get()
        )
    }
    single {
        UntappdService(
            ktorClient = get(),
            tokenStore = get()
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

