package data

import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

@Serializable
data class LoginUrl(
    val loginUrl: String,
)

@Serializable
data class AuthenticationCode(
    val code: String
)

@Serializable
data class AuthenticationResponse(
    val access_token: String
)

private const val BASE_URL = "beers-kmp.gcaguilar.workers.dev"
private const val LOGIN = "login"
private const val AUTHORIZE = "authorize"

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

    suspend fun authorize(authenticationCode :AuthenticationCode): Result<AuthenticationResponse> {
        return ktorClient.fetch<AuthenticationResponse> {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                method = HttpMethod.Post
                appendPathSegments(AUTHORIZE)
                contentType(ContentType.Application.Json)
                setBody(authenticationCode)
            }
        }
    }
}
