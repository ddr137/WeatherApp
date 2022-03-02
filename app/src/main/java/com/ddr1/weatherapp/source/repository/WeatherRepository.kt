package com.ddr1.weatherapp.source.repository

import com.ddr1.weatherapp.model.WeatherModel
import com.ddr1.weatherapp.source.network.ApiClient
import com.ddr1.weatherapp.viewmodel.WeatherViewModel
import org.koin.dsl.module

val repository = module {
    factory { WeatherRepository(get())  }
}
class WeatherRepository(
    private val api: ApiClient
) {

    suspend fun fetch(
        lat: Double,
        lon: Double,
        exclude: String,
        cnt: Int,
        units: String,
        apikey: String,
    ): WeatherModel {
        return api.fetchWeather(lat, lon, exclude, cnt, units, apikey)
    }
}