package com.sergioozzon.sergei_smirnov_interval_timer.ui

import androidx.lifecycle.ViewModel
import com.learning.intervaltimer.domain.WorkoutDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WorkoutSharedViewModel : ViewModel() {

    private val _workout = MutableStateFlow<WorkoutDTO?>(null)
    val workout = _workout.asStateFlow()

    fun setWorkout(workoutDTO: WorkoutDTO) {
        _workout.value = workoutDTO
    }

    fun clearWorkout() {
        _workout.value = null
    }
}
