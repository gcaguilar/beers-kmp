package data

import domain.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val authenticationService: AuthenticationService
) : AuthenticationRepository {
    override suspend fun getLoginUrl(): Result<String> {
        return authenticationService.getAuthenticationUrl().map {
            it.loginUrl
        }
    }

    override suspend fun authenticate(code: String): Result<Boolean> {
        return authenticationService.authenticate(AuthenticationCode(code))
            .mapCatching { token ->
                true
            }
    }
}
