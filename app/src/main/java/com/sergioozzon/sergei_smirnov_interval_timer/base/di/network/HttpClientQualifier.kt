package com.learning.intervaltimer.di.network

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

sealed interface HttpClientQualifier : Qualifier {

    object WorkoutQualifier : HttpClientQualifier {
        override val value: QualifierValue = "WorkoutHttpClient"
    }
}