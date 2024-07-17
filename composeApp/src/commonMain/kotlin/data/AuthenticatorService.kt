package data

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.utils.*
import io.ktor.http.*
import org.publicvalue.multiplatform.oidc.OpenIdConnectClient
import org.publicvalue.multiplatform.oidc.appsupport.CodeAuthFlowFactory

interface AuthenticatorService {
    suspend fun authenticate(): Result<String>
}

class AuthenticationServiceImpl(
    private val client: OpenIdConnectClient,
    private val codeAuthFlowFactory: CodeAuthFlowFactory
) : AuthenticatorService {
    override suspend fun authenticate(): Result<String> {
        return try {
        val response = codeAuthFlowFactory.createAuthFlow(client)
            .getAccessToken(
                configureTokenExchange = {
                    headers.append("User-Agent", "BeersKMM D912C0B80A28A04F4EA2FD48E625853326BEAB1C")
                    method = HttpMethod.Get
                    val formData = (body as FormDataContent).formData
                    formData.entries().forEach { (key, value) ->
                        if (key != "grant_type") {
                            url.parameters.append(key, value.first())
                        }
                        if (key == "redirect_uri") {
                            url.parameters.append("redirect_url", value.first())
                        }
                    }
                    setBody(EmptyContent)
                }
            )
            Result.success(response.access_token)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
