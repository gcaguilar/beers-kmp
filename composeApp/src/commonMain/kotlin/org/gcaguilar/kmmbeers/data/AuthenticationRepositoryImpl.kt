package org.gcaguilar.kmmbeers.data

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapCatching
import org.gcaguilar.kmmbeers.domain.AuthenticationRepository
import org.publicvalue.multiplatform.oidc.ExperimentalOpenIdConnect
import org.publicvalue.multiplatform.oidc.tokenstore.TokenStore

class AuthenticationRepositoryImpl @OptIn(ExperimentalOpenIdConnect::class) constructor(
    private val authenticatorService: AuthenticatorService,
    private val tokenStore: TokenStore,
    private val firebaseService: FireStoreService
) : AuthenticationRepository {
    @OptIn(ExperimentalOpenIdConnect::class)
    override suspend fun authenticate(): Result<Unit, Throwable> {
        return authenticatorService.authenticate(
            clientId = firebaseService.getClientId(),
            clientSecret = firebaseService.getClientSecret()
        ).mapCatching {
            tokenStore.saveTokens(accessToken = it, refreshToken = null, idToken = null)
        }
    }

    @OptIn(ExperimentalOpenIdConnect::class)
    override suspend fun isLoggedIn(): Boolean {
        return tokenStore.getAccessToken() != null
    }
}
