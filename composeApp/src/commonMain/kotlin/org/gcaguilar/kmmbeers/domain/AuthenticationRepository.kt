package org.gcaguilar.kmmbeers.domain

interface AuthenticationRepository {
    suspend fun authenticate()
  
    suspend fun isLoggedIn(): Boolean
}
