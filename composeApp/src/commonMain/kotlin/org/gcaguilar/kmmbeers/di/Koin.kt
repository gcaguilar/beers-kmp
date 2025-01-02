package org.gcaguilar.kmmbeers.di

import co.touchlab.kermit.Logger
import dev.gitlive.firebase.Firebase
//import dev.gitlive.firebase.Firebase
//import dev.gitlive.firebase.FirebaseApp
//import io.github.jan.supabase.auth.Auth
//import io.github.jan.supabase.compose.auth.ComposeAuth
//import io.github.jan.supabase.compose.auth.appleNativeLogin
//import io.github.jan.supabase.compose.auth.googleNativeLogin
//import io.github.jan.supabase.createSupabaseClient
//import io.github.jan.supabase.postgrest.Postgrest
import org.gcaguilar.kmmbeers.domain.*
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.gcaguilar.kmmbeers.data.*
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.publicvalue.multiplatform.oidc.ExperimentalOpenIdConnect
import org.publicvalue.multiplatform.oidc.OpenIdConnectClient
import org.publicvalue.multiplatform.oidc.types.CodeChallengeMethod
import org.gcaguilar.kmmbeers.presentation.authentication.LoginViewModel
import org.gcaguilar.kmmbeers.presentation.search.SearchViewModel
import org.gcaguilar.kmmbeers.presentation.splash.SplashViewModel
import org.gcaguilar.kmmbeers.presentation.beer.BeerDetailViewModel
import org.gcaguilar.kmmbeers.presentation.brewery.BreweryDetailViewModel
import org.koin.core.module.dsl.viewModelOf

val presentationModule = module {
    viewModelOf(::SearchViewModel)
    viewModelOf(::BeerDetailViewModel)
    viewModelOf(::BreweryDetailViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::SplashViewModel)
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
        Firebase
    }

    factory {
        FireStoreService(
            firebase = get()
        )
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
            client = get(),
            codeAuthFlowFactory = get()
        )
    }
    factory<AuthenticationRepository> {
        AuthenticationRepositoryImpl(
            authenticatorService = get(),
            tokenStore = get(),
            firebaseService = get()
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
            install(Logging) {
                logger = object : io.ktor.client.plugins.logging.Logger {
                    override fun log(message: String) {
                        Logger.d("HTTP Client", null, message)
                    }
                }
                level = LogLevel.ALL
                sanitizeHeader { header -> header == HttpHeaders.Authorization }
            }
        }
    }
}

