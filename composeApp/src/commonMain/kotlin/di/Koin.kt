package di


import data.SearchRepositoryImpl
import data.UntappdService
import domain.GetBeerDetail
import domain.SearchBeer
import domain.SearchRepository
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.detail.DetailViewModel
import presentation.brewery.BreweryDetailViewModel
import presentation.search.SearchViewModel

fun initKoin() =
    startKoin {
        modules(networkModule + dataModule + domainModule + presentationModule)
    }

private val presentationModule = module {
    viewModelOf(::SearchViewModel)
    viewModelOf (::DetailViewModel)
    viewModelOf (::BreweryDetailViewModel)
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
}

private val dataModule = module {
    factory<SearchRepository> {
        SearchRepositoryImpl(
            service = get()
        )   
    }
    
    single {
        UntappdService(
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
                logger = Logger.SIMPLE
                level = LogLevel.BODY
            }
        }
    }

    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }
}