package com.ddr1.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddr1.weatherapp.BuildConfig
import com.ddr1.weatherapp.model.WeatherModel
import com.ddr1.weatherapp.source.repository.WeatherRepository
import kotlinx.coroutines.launch
import org.koin.dsl.module
import timber.log.Timber

val viewModel = module {
factory { WeatherViewModel(get())  }
}
class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    val weather by lazy { MutableLiveData<WeatherModel>() }
    val message by lazy { MutableLiveData<String?>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    var lat = -6.917464
    var lon = 107.619125
    var exclude = "alerts"
    var cnt = 3
    var units = "metric"
    var apikey = BuildConfig.API_KEY

    fun fetchWeather() {
        loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.fetch(lat, lon, exclude, cnt, units, apikey)
                weather.value = response
                loading.value = false
            } catch (e: Exception) {
                Timber.d("kanckok: $e")
                message.value = "Terjadi Kesalahan"
            }
        }
    }
}