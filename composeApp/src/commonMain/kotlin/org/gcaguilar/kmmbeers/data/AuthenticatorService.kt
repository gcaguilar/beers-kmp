package org.gcaguilar.kmmbeers.data

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
    private val fireStoreService: FireStoreService,
    private val client: OpenIdConnectClient,
    private val codeAuthFlowFactory: CodeAuthFlowFactory
) : AuthenticatorService {
    override suspend fun authenticate(): Result<String> {
        client.config.clientId = fireStoreService.clientId()
        client.config.clientSecret = fireStoreService.clientSecret()

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
            Result.success(response.access_token)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
