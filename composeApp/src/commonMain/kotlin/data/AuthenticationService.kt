package data

import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments
import kotlinx.serialization.Serializable

@Serializable
data class LoginUrl(
    val loginUrl: String,
)

@Serializable
data class AuthenticationCode(
    val code: String
)

private const val BASE_URL = "beers-kmp.gcaguilar.workers.dev"
private const val LOGIN = "login"
private const val AUTHENTICATE = "login"

class AuthenticationService(
    private val ktorClient: HttpClient,
) {
    suspend fun getAuthenticationUrl(): Result<LoginUrl> {
        return ktorClient.fetch<LoginUrl> {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                method = HttpMethod.Get
                appendPathSegments(LOGIN)
            }
        }
    }

    suspend fun authenticate(authenticationCode :AuthenticationCode): Result<String> {
        return ktorClient.fetch<String> {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                method = HttpMethod.Post
                appendPathSegments(AUTHENTICATE)
                setBody(authenticationCode)
            }
        }
    }
}
