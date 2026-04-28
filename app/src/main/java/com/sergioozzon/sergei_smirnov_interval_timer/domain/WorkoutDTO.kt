package com.sergioozzon.sergei_smirnov_interval_timer.domain

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class WorkoutDTO(
    val timer: TimerDTO?,
)

@Immutable
@Serializable
data class TimerDTO(
    val id: Int,
    val title: String,
    val totalTime: Int,
    val intervals: List<IntervalDTO>,
)

@Immutable
@Serializable
data class IntervalDTO(
    val title: String,
    val time: Int,
)
