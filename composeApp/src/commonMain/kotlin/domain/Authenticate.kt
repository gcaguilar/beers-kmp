package domain

class Authenticate(
    private val authenticationRepository: AuthenticationRepository
){
    suspend operator fun invoke() {
        return authenticationRepository.authenticate()
    }
}