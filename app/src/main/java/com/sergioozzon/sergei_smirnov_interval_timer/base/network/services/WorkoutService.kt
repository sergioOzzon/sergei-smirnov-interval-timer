package com.sergioozzon.sergei_smirnov_interval_timer.base.network.services

import com.sergioozzon.sergei_smirnov_interval_timer.base.network.models.responses.WorkoutResponseBody
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path

interface WorkoutService {
    @GET("interval-timers/{id}")
    suspend fun getWorkout(
        @Path("id") workoutId: String,
    ): WorkoutResponseBody

}