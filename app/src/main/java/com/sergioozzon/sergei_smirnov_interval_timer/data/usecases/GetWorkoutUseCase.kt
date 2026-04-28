package com.sergioozzon.sergei_smirnov_interval_timer.data.usecases

import com.sergioozzon.sergei_smirnov_interval_timer.base.network.mappers.toDTO
import com.sergioozzon.sergei_smirnov_interval_timer.base.network.services.WorkoutService
import com.sergioozzon.sergei_smirnov_interval_timer.domain.WorkoutDTO

interface GetWorkoutUseCase {

    suspend operator fun invoke(
        workoutId: String,
    ): Result<WorkoutDTO>

}

internal class GetWorkoutUseCaseImpl(
    private val workoutService: WorkoutService,
) : GetWorkoutUseCase {

    override suspend fun invoke(workoutId: String): Result<WorkoutDTO> {
        return runCatching {
            workoutService
                .getWorkout(workoutId)
                .toDTO()
        }
    }

}