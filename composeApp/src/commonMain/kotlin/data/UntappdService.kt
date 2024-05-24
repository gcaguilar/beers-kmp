package data

import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments

private const val BASE_URL = "api.untappd.com"
private const val V4 = "v4"
private const val SEARCH = "search"
private const val BEER = "beer"
private const val Brewery = "beer"
private const val LIMIT = 50
private const val INFO = "info"
private const val BID = "BID"

private const val SEARCH_BEER_ENDPOINT = "${BASE_URL}${SEARCH}${BEER}"

class UntappdService(
    private val ktorClient: HttpClient
) {
    suspend fun searchBeer(searchName: String): Result<SearchBeerResponse> {
        return ktorClient.fetch {
            url {
                protocol = URLProtocol.HTTP
                port = 3000
                host = "localhost"
                method = HttpMethod.Get
                appendPathSegments(V4, SEARCH, BEER)
//                with(parameters) {
//                    append("q", searchName)
//                    append("access_token", "00B0B63B9F11949F51DE35A1EBE9548C3EBCA433")
//                }
            }
        }
    }

    suspend fun getBeerDetail(bid: String): Result<BeerDetailResponse> {
        return ktorClient.fetch {
            url {
//                protocol = URLProtocol.HTTP
                port = 3000
//                host = "api.untappd.com"
                host = "localhost"
                method = HttpMethod.Get
                appendPathSegments(V4, BEER, INFO, bid)
//                with(parameters) {
//                    append("access_token", "00B0B63B9F11949F51DE35A1EBE9548C3EBCA433")
//                    append("compact", "true")
//                }
            }
        }
    }
    
    suspend fun getBreweryDetail(id: String): Result<BreweryDetailResponse> {
        return ktorClient.fetch {
            url {
                //                protocol = URLProtocol.HTTP
                port = 3000
                //                host = "api.untappd.com"
                host = "localhost"
                method = HttpMethod.Get
                appendPathSegments(V4, Brewery, INFO, id)
            //                with(parameters) {
            //                    append("access_token", "00B0B63B9F11949F51DE35A1EBE9548C3EBCA433")
            //                    append("compact", "true")
            //                }
            }
        }
    }
}
