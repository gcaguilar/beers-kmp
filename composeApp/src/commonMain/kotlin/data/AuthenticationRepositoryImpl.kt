package data

import domain.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val authenticationService: AuthenticationService,
    private val secureStorage: SecureStorage
) : AuthenticationRepository {
    override suspend fun getLoginUrl(): Result<String> {
        return authenticationService.getAuthenticationUrl().mapCatching {
            it.loginUrl
        }
    }

    override suspend fun authenticate(code: String): Result<Unit> {
        return authenticationService.authorize(AuthenticationCode(code))
            .mapCatching { secureStorage.setAccessToken(it.access_token) }
    }

    override fun isLoggedIn(): Boolean {
        return secureStorage.isLoggedIn()
    }
}
