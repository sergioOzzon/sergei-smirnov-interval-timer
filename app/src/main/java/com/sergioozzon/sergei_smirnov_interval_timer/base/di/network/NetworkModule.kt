package com.sergioozzon.sergei_smirnov_interval_timer.base.di.network

import com.sergioozzon.sergei_smirnov_interval_timer.base.network.API_BASE_URL
import com.sergioozzon.sergei_smirnov_interval_timer.base.network.HttpClientFactory
import com.sergioozzon.sergei_smirnov_interval_timer.base.network.NetworkResponseHandler
import com.sergioozzon.sergei_smirnov_interval_timer.base.network.services.WorkoutService
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        }
    }
    singleOf(::HttpClientFactory)
    singleOf(::NetworkResponseHandler)

    single<HttpClient>(qualifier = HttpClientQualifier.WorkoutQualifier) {
        get<HttpClientFactory>().createWorkoutHttpClient(
            networkResponseHandler = get()
        )
    }
    single<Ktorfit>(qualifier = KtorfitQualifier.WorkoutQualifier) {
        ktorfit {
            baseUrl(url = API_BASE_URL)
            httpClient(client = get(HttpClientQualifier.WorkoutQualifier))
        }
    }

    single<WorkoutService> {
        get<Ktorfit>(qualifier = KtorfitQualifier.WorkoutQualifier).createWorkoutService()
    }
}