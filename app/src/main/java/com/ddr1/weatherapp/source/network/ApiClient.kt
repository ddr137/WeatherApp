package com.ddr1.weatherapp.source.network

import com.ddr1.weatherapp.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("onecall")
    suspend fun fetchWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String,
        @Query("cnt") cnt: Int,
        @Query("units") units: String,
        @Query("appid") apikey: String,
    ) : WeatherModel

}