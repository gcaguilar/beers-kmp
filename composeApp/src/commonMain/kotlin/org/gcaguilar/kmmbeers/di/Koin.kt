package org.gcaguilar.kmmbeers.di

import org.gcaguilar.kmmbeers.domain.*
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.gcaguilar.kmmbeers.data.*
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.publicvalue.multiplatform.oidc.ExperimentalOpenIdConnect
import org.publicvalue.multiplatform.oidc.OpenIdConnectClient
import org.publicvalue.multiplatform.oidc.types.CodeChallengeMethod
import org.gcaguilar.kmmbeers.presentation.authentication.LoginScreenModel
import org.gcaguilar.kmmbeers.presentation.search.SearchScreenModel
import org.gcaguilar.kmmbeers.presentation.splash.SplashScreenModel
import org.gcaguilar.kmmbeers.presentation.beer.BeerDetailViewModel
import org.gcaguilar.kmmbeers.presentation.brewery.BreweryDetailViewModel
import org.koin.core.module.dsl.viewModelOf

val presentationModule = module {
    viewModelOf(::SearchScreenModel)
    viewModelOf(::BeerDetailViewModel)
    viewModelOf(::BreweryDetailViewModel)
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
    single {
        FireStoreService()
    }

    single {
        OpenIdConnectClient {
            endpoints {
                tokenEndpoint = "https://untappd.com/oauth/authorize/"
                authorizationEndpoint = "https://untappd.com/oauth/authenticate/"
                userInfoEndpoint = null
                endSessionEndpoint = ""
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
    factory<AuthenticatorService> {
        AuthenticationServiceImpl(
            fireStoreService = get(),
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

