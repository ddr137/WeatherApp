package com.ddr1.weatherapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.ddr1.weatherapp.source.network.networkModule
import com.ddr1.weatherapp.source.repository.repository
import com.ddr1.weatherapp.ui.mainModule
import com.ddr1.weatherapp.viewmodel.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@WeatherApp)
            modules(
                networkModule,
                repository,
                viewModel,
                mainModule,
            )
        }
    }
}