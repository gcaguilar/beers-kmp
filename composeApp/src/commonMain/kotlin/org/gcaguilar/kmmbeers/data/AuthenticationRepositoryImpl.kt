package org.gcaguilar.kmmbeers.data

import org.gcaguilar.kmmbeers.domain.AuthenticationRepository
import org.publicvalue.multiplatform.oidc.ExperimentalOpenIdConnect
import org.publicvalue.multiplatform.oidc.tokenstore.TokenStore

class AuthenticationRepositoryImpl @OptIn(ExperimentalOpenIdConnect::class) constructor(
    private val authenticatorService: AuthenticatorService,
    private val tokenStore: TokenStore
) : AuthenticationRepository {
    @OptIn(ExperimentalOpenIdConnect::class)
    override suspend fun authenticate() {
        authenticatorService.authenticate()
            .onSuccess {
                tokenStore.saveTokens(accessToken = it, refreshToken = null, idToken = null)
            }
    }

    @OptIn(ExperimentalOpenIdConnect::class)
    override suspend fun isLoggedIn(): Boolean {
        return tokenStore.getAccessToken() != null
    }
}
