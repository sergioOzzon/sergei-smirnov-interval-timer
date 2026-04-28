package com.sergioozzon.sergei_smirnov_interval_timer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sergioozzon.sergei_smirnov_interval_timer.ui.searchworkout.SearchWorkoutScreen
import com.sergioozzon.sergei_smirnov_interval_timer.ui.workout.WorkoutScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destination.SearchWorkout
    ) {
        composable<Destination.SearchWorkout> {
            SearchWorkoutScreen(navController)
        }
        composable<Destination.Workout> {
            WorkoutScreen(navController)
        }
    }
}
