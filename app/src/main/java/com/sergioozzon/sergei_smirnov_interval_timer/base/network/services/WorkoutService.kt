package com.sergioozzon.sergei_smirnov_interval_timer.base.network.services

import com.sergioozzon.sergei_smirnov_interval_timer.base.network.models.responses.WorkoutResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WorkoutService {
    @GET("interval-timers/{id}")
    suspend fun getWorkout(
        @Path("id") workoutId: String,
    ): Response<WorkoutResponseBody>

}
