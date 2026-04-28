package com.sergioozzon.sergei_smirnov_interval_timer.domain

import kotlinx.serialization.Serializable

@Serializable
data class WorkoutDTO(
    val timer: TimerDTO?,
)

@Serializable
data class TimerDTO(
    val id: Int,
    val title: String,
    val totalTime: Int,
    val intervals: List<IntervalDTO>,
)

@Serializable
data class IntervalDTO(
    val title: String,
    val time: Int,
)
