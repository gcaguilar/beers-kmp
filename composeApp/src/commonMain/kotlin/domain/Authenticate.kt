package domain

class Authenticate(
    private val authenticationRepository: AuthenticationRepository
){
    suspend operator fun invoke(code: String): Result<Unit> {
        return authenticationRepository.authenticate(code)
    }
}