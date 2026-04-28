package com.sergioozzon.sergei_smirnov_interval_timer.base.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpCallValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class HttpClientFactory(
    private val json: Json,
) {

    fun createWorkoutHttpClient(
        networkResponseHandler: NetworkResponseHandler,
    ): HttpClient {
        return HttpClient(OkHttp) {
            expectSuccess = true

            if (BuildConfig.DEBUG) {
                install(Logging) {
                    logger = Logger.SIMPLE
                    level = LogLevel.ALL
                }
            }

            install(ContentNegotiation) {
                json(json)
            }

            install(HttpTimeout) {
                connectTimeoutMillis = 30000
                requestTimeoutMillis = 30000
                socketTimeoutMillis = 30000
            }

            defaultRequest {
                url(API_BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header(HttpHeaders.Authorization, BEARER_TEST_TOKEN)
                header(APP_TOKEN_KEY, APP_TOKEN_VALUE)
            }

            install(HttpCallValidator) {
                handleResponseExceptionWithRequest(networkResponseHandler::handleResponseError)
            }
        }
    }
}