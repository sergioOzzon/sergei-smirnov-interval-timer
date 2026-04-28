package com.sergioozzon.sergei_smirnov_interval_timer.data.usecases

import com.sergioozzon.sergei_smirnov_interval_timer.base.network.mappers.toDTO
import com.sergioozzon.sergei_smirnov_interval_timer.base.network.NetworkResponseHandler
import com.sergioozzon.sergei_smirnov_interval_timer.base.network.services.WorkoutService
import com.sergioozzon.sergei_smirnov_interval_timer.domain.WorkoutDTO

interface GetWorkoutUseCase {

    suspend operator fun invoke(
        workoutId: String,
    ): Result<WorkoutDTO>

}

internal class GetWorkoutUseCaseImpl(
    private val workoutService: WorkoutService,
    private val networkResponseHandler: NetworkResponseHandler,
) : GetWorkoutUseCase {

    override suspend fun invoke(workoutId: String): Result<WorkoutDTO> {
        return runCatching {
            val response = workoutService.getWorkout(workoutId)
            val body = response.body()

            if (response.isSuccessful && body != null) {
                body.toDTO()
            } else {
                throw networkResponseHandler.handleHttpError(
                    code = response.code(),
                    message = response.message()
                )
            }
        }
    }

}
