package domain

class IsLoggedIn(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): Boolean = authenticationRepository.isLoggedIn()
}