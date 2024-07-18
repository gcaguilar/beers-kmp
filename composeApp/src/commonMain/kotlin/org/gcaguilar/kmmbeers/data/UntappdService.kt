package org.gcaguilar.kmmbeers.data

import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments
import org.publicvalue.multiplatform.oidc.ExperimentalOpenIdConnect
import org.publicvalue.multiplatform.oidc.tokenstore.TokenStore

private const val BASE_URL = "api.untappd.com"
private const val V4 = "v4"
private const val SEARCH = "search"
private const val BEER = "beer"
private const val Brewery = "brewery"
private const val INFO = "info"

class UntappdService @OptIn(ExperimentalOpenIdConnect::class) constructor(
    private val ktorClient: HttpClient,
    private val tokenStore: TokenStore
) {
    @OptIn(ExperimentalOpenIdConnect::class)
    suspend fun searchBeer(searchName: String, offset: Int? = null): Result<SearchBeerResponse> {
        val token = tokenStore.getAccessToken()!!
        return ktorClient.fetch {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                method = HttpMethod.Get
                appendPathSegments(V4, SEARCH, BEER)
                with(parameters) {
                    append("q", searchName)
                    offset?.let { append("offset", it.toString()) }
                    append("access_token", token)
                }
            }
        }
    }

    @OptIn(ExperimentalOpenIdConnect::class)
    suspend fun getBeerDetail(bid: String): Result<BeerDetailResponse> {
        val token = tokenStore.getAccessToken()!!
        return ktorClient.fetch {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                method = HttpMethod.Get
                appendPathSegments(V4, BEER, INFO, bid)
                with(parameters) {
                    append("access_token", token)
                    append("compact", "true")
                }
            }
        }
    }

    @OptIn(ExperimentalOpenIdConnect::class)
    suspend fun getBreweryDetail(id: String): Result<BreweryDetailResponse> {
        val token = tokenStore.getAccessToken()!!

        return ktorClient.fetch {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                method = HttpMethod.Get
                appendPathSegments(V4, Brewery, INFO, id)
                with(parameters) {
                    append("access_token", token)
                    append("compact", "true")
                }
            }
        }
    }
}
