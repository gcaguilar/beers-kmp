package data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T> HttpClient.fetch(
    block: HttpRequestBuilder.() -> Unit
): Result<T> = try {
    val response = request(block)

    if (response.status == HttpStatusCode.OK) {
        Result.success(response.body())
    } else {
        Result.failure(Throwable("${response.status}: ${response.bodyAsText()}"))
    }
} catch (e: Exception) {
    Result.failure(e)
}