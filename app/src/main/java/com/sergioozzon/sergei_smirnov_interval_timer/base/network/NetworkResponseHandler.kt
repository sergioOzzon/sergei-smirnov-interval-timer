package com.sergioozzon.sergei_smirnov_interval_timer.base.network

import io.ktor.client.request.HttpRequest

class NetworkResponseHandler {
    suspend fun handleResponseError(cause: Throwable, request: HttpRequest) {
        throw when (cause) {
            else -> {
                UnknownError(message = cause.message, cause = cause)
            }
        }
    }
}

sealed class NetworkError(
    message: String? = null,
    cause: Throwable? = null,
) : Exception(message, cause)

data class UnknownError(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : NetworkError(message = message, cause = cause)


