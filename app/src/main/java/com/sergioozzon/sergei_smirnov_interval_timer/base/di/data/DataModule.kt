package com.sergioozzon.sergei_smirnov_interval_timer.base.di.data

import com.sergioozzon.sergei_smirnov_interval_timer.base.data.usecases.GetWorkoutUseCase
import com.sergioozzon.sergei_smirnov_interval_timer.base.data.usecases.GetWorkoutUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    // UseCases
    factoryOf(::GetWorkoutUseCaseImpl) bind GetWorkoutUseCase::class

}