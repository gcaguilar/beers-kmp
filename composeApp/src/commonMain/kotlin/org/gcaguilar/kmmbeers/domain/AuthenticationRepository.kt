package org.gcaguilar.kmmbeers.domain

import com.github.michaelbull.result.Result

interface AuthenticationRepository {
    suspend fun authenticate(): Result<Unit, Throwable>

    suspend fun isLoggedIn(): Boolean
}
