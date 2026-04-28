package com.sergioozzon.sergei_smirnov_interval_timer.base.di.network

import com.sergioozzon.sergei_smirnov_interval_timer.base.network.API_BASE_URL
import com.sergioozzon.sergei_smirnov_interval_timer.base.network.CONTENT_TYPE_VALUE
import com.sergioozzon.sergei_smirnov_interval_timer.base.network.NetworkResponseHandler
import com.sergioozzon.sergei_smirnov_interval_timer.base.network.OkHttpClientFactory
import com.sergioozzon.sergei_smirnov_interval_timer.base.network.services.WorkoutService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val networkModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        }
    }
    singleOf(::OkHttpClientFactory)
    singleOf(::NetworkResponseHandler)

    single<OkHttpClient>(qualifier = HttpClientQualifier.WorkoutQualifier) {
        get<OkHttpClientFactory>().createWorkoutOkHttpClient(
            networkResponseHandler = get()
        )
    }

    single<Retrofit>(qualifier = RetrofitQualifier.WorkoutQualifier) {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(get(HttpClientQualifier.WorkoutQualifier))
            .addConverterFactory(get<Json>().asConverterFactory(CONTENT_TYPE_VALUE.toMediaType()))
            .build()
    }

    single<WorkoutService> {
        get<Retrofit>(qualifier = RetrofitQualifier.WorkoutQualifier).create(WorkoutService::class.java)
    }
}
