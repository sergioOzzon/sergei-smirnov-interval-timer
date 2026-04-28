package com.sergioozzon.sergei_smirnov_interval_timer.base.network

class NetworkResponseHandler {
    fun handleResponseError(cause: Throwable): NetworkError {
        return when (cause) {
            else -> {
                UnknownError(message = cause.message, cause = cause)
            }
        }
    }

    fun handleHttpError(code: Int, message: String): NetworkError {
        return when (code) {
            404 -> NotFoundError(message = "HTTP $code: $message")
            else -> UnknownError(message = "HTTP $code: $message")
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

data class NotFoundError(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : NetworkError(message = message, cause = cause)
