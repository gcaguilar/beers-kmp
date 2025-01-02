package org.gcaguilar.kmmbeers.domain

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError

class Authenticate(
    private val authenticationRepository: AuthenticationRepository
) {
    data object AuthenticationError

    suspend operator fun invoke(): Result<Unit, AuthenticationError> {
        return authenticationRepository.authenticate().mapError {
            AuthenticationError
        }
    }
}