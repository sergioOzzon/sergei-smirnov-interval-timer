package com.sergioozzon.sergei_smirnov_interval_timer.base.di.network

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

sealed interface KtorfitQualifier : Qualifier {

    object WorkoutQualifier : KtorfitQualifier {
        override val value: QualifierValue = "WorkoutHttpClient"
    }
}