package domain

class IsLoggedIn(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(): Boolean = authenticationRepository.isLoggedIn()
}