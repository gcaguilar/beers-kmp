package data

import io.github.aakira.napier.Napier
import io.github.alexandereggers.ksecurestorage.IKSecureStorage
import io.github.alexandereggers.ksecurestorage.KSecureStorage
import io.github.alexandereggers.ksecurestorage.getString

private const val ACCESS_TOKEN = "access_token"

class SecureStorage(
    private val kSecureStorage: KSecureStorage
) {
    fun setAccessToken(token: String) {
        return kSecureStorage.setItem(ACCESS_TOKEN, token)
    }

    fun getAccessToken(): String {
        return kSecureStorage.getString(ACCESS_TOKEN) ?: ""
    }

    fun isLoggedIn(): Boolean {
        val token = kSecureStorage.getString(ACCESS_TOKEN)
        Napier.d("Checking for logged in ${token ?: "Token Null"}")
        return !token.isNullOrBlank ()
    }
}