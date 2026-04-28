package com.sergioozzon.sergei_smirnov_interval_timer

import android.app.Application
import com.sergioozzon.sergei_smirnov_interval_timer.base.di.data.dataModule
import com.sergioozzon.sergei_smirnov_interval_timer.base.di.mainModule
import com.sergioozzon.sergei_smirnov_interval_timer.base.di.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class IntervalTimerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(applicationContext)

            modules(
                mainModule,
                dataModule,
                networkModule
            )
        }
    }
}