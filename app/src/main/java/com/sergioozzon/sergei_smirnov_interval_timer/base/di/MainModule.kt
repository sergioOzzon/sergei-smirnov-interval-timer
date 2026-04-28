package com.sergioozzon.sergei_smirnov_interval_timer.base.di

import com.sergioozzon.sergei_smirnov_interval_timer.ui.WorkoutSharedViewModel
import com.sergioozzon.sergei_smirnov_interval_timer.ui.searchworkout.SearchWorkoutViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {

    viewModelOf(::SearchWorkoutViewModel)

    single { WorkoutSharedViewModel() }

}
