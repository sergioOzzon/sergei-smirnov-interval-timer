package com.sergioozzon.sergei_smirnov_interval_timer.base.network.mappers

import com.sergioozzon.sergei_smirnov_interval_timer.base.network.models.responses.IntervalResponseBody
import com.sergioozzon.sergei_smirnov_interval_timer.base.network.models.responses.TimerResponseBody
import com.sergioozzon.sergei_smirnov_interval_timer.base.network.models.responses.WorkoutResponseBody
import com.sergioozzon.sergei_smirnov_interval_timer.domain.IntervalDTO
import com.sergioozzon.sergei_smirnov_interval_timer.domain.TimerDTO
import com.sergioozzon.sergei_smirnov_interval_timer.domain.WorkoutDTO

fun WorkoutResponseBody.toDTO(): WorkoutDTO {
    return WorkoutDTO(
        timer = timer.toDTO()
    )
}

fun TimerResponseBody.toDTO(): TimerDTO {
    return TimerDTO(
        id = timerId,
        title = title,
        totalTime = totalTime,
        intervals = intervals.map { it.toDTO() }
    )
}

fun IntervalResponseBody.toDTO(): IntervalDTO {
    return IntervalDTO(
        title = title,
        time = time
    )
}
