package com.sergioozzon.sergei_smirnov_interval_timer.base.network.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkoutResponseBody(
    @SerialName("timer") val timer: TimerResponseBody,
)

@Serializable
data class TimerResponseBody(
    @SerialName("timer_id") val timerId: Int,
    @SerialName("title") val title: String,
    @SerialName("total_time") val totalTime: Int,
    @SerialName("intervals") val intervals: List<IntervalResponseBody>,
)

@Serializable
data class IntervalResponseBody(
    @SerialName("title") val title: String,
    @SerialName("time") val time: Int,
)

