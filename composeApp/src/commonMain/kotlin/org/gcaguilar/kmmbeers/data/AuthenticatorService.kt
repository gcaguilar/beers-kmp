package org.gcaguilar.kmmbeers.data

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.utils.*
import io.ktor.http.*
import org.publicvalue.multiplatform.oidc.OpenIdConnectClient
import org.publicvalue.multiplatform.oidc.appsupport.CodeAuthFlowFactory
import com.github.michaelbull.result.Result

interface AuthenticatorService {
    suspend fun authenticate(clientId: String, clientSecret: String): Result<String, Throwable>
}

class AuthenticationServiceImpl(
    private val client: OpenIdConnectClient,
    private val codeAuthFlowFactory: CodeAuthFlowFactory
) : AuthenticatorService {
    override suspend fun authenticate(clientId: String, clientSecret: String): Result<String, Throwable> {
        client.config.clientId = clientId
        client.config.clientSecret = clientSecret

        return try {
            val response = codeAuthFlowFactory.createAuthFlow(client)
                .getAccessToken(
                    configureTokenExchange = {
                        headers.append("User-Agent", "BeersKMM ${client.config.clientId}")
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
            Ok(response.access_token)
        } catch (e: Exception) {
            Err(e)
        }
    }
}
