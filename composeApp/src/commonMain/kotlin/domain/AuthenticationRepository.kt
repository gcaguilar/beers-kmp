package domain

interface AuthenticationRepository {
    suspend fun getLoginUrl(): Result<String>
    suspend fun authenticate(code: String): Result<Unit>
    fun isLoggedIn(): Boolean
}
