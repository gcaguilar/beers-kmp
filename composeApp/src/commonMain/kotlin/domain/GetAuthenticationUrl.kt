package domain

class GetAuthenticationUrl(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): Result<String> = authenticationRepository.getLoginUrl()
}
