package com.sergioozzon.sergei_smirnov_interval_timer.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Destination {

    @Serializable
    data object SearchWorkout : Destination()
    
    @Serializable
    data object Workout : Destination()
}
