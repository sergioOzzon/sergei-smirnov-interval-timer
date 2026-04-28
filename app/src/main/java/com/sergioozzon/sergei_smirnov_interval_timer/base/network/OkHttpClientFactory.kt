package com.sergioozzon.sergei_smirnov_interval_timer.base.network

import com.sergioozzon.sergei_smirnov_interval_timer.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class OkHttpClientFactory {

    fun createWorkoutOkHttpClient(
        networkResponseHandler: NetworkResponseHandler,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(createHeadersInterceptor())
            .addInterceptor(createErrorInterceptor(networkResponseHandler))
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                }
            }
            .build()
    }

    private fun createHeadersInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .header(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE)
                .header(AUTHORIZATION_KEY, BEARER_TEST_TOKEN)
                .header(APP_TOKEN_KEY, APP_TOKEN_VALUE)
                .build()

            chain.proceed(request)
        }
    }

    private fun createErrorInterceptor(
        networkResponseHandler: NetworkResponseHandler,
    ): Interceptor {
        return Interceptor { chain ->
            runCatching {
                chain.proceed(chain.request())
            }.getOrElse { cause ->
                throw networkResponseHandler.handleResponseError(cause)
            }
        }
    }
}
