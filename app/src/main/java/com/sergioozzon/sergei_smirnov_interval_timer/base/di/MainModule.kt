package com.sergioozzon.sergei_smirnov_interval_timer.base.di

import com.sergioozzon.sergei_smirnov_interval_timer.base.sound.SoundPoolWorkoutSoundService
import com.sergioozzon.sergei_smirnov_interval_timer.base.sound.WorkoutSoundService
import com.sergioozzon.sergei_smirnov_interval_timer.ui.WorkoutSharedViewModel
import com.sergioozzon.sergei_smirnov_interval_timer.ui.searchworkout.SearchWorkoutViewModel
import com.sergioozzon.sergei_smirnov_interval_timer.ui.workout.WorkoutViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val mainModule = module {

    factory {
        SoundPoolWorkoutSoundService(androidContext())
    } bind WorkoutSoundService::class

    viewModelOf(::SearchWorkoutViewModel)
    viewModelOf(::WorkoutViewModel)
    single { WorkoutSharedViewModel() }

}
