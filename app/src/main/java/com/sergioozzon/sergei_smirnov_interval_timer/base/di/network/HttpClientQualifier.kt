package com.sergioozzon.sergei_smirnov_interval_timer.base.di.network

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

sealed interface HttpClientQualifier : Qualifier {

    data object WorkoutQualifier : HttpClientQualifier {
        override val value: QualifierValue = "WorkoutOkHttpClient"
    }
}
