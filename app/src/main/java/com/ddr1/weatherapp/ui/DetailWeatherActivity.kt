package com.ddr1.weatherapp.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import com.ddr1.weatherapp.R
import com.ddr1.weatherapp.databinding.ActivityDetailWeatherBinding
import com.ddr1.weatherapp.databinding.CustomToolbarBinding
import com.ddr1.weatherapp.model.WeatherModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

class DetailWeatherActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailWeatherBinding.inflate(layoutInflater) }
    private lateinit var bindingToolbar: CustomToolbarBinding

    private val detail by lazy {
        intent.getSerializableExtra("intent_detail") as WeatherModel.Daily
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingToolbar = binding.toolbar
        setContentView(binding.root)

        setSupportActionBar(bindingToolbar.container)
        supportActionBar!!.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        val dateData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ISO_INSTANT.format(Instant.ofEpochSecond(detail.dt.toLong()))
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val currentFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val dateParse = currentFormat.parse(dateData)
        val day = DateFormat.format("EEEE", dateParse) as String
        val date = DateFormat.format("d MMMM yyyy", dateParse) as String

        val c = Calendar.getInstance()
        val timeOfDay = c[Calendar.HOUR_OF_DAY]
        var tempHour = 0.0

        when (timeOfDay) {
            in 0..11 -> {
                tempHour = detail.temp.morn
            }
            in 12..15 -> {
                tempHour = detail.temp.day
            }
            in 16..20 -> {
                tempHour = detail.temp.eve
            }
            in 21..23 -> {
                tempHour = detail.temp.night
            }
        }

        binding.apply {
            tvDay.text = day.format(dateParse)
            tvDate.text = date.format(dateParse)
            tvLocation.text = "Lokasi Bandung"
            tvTemperature.text = String.format(Locale.getDefault(), "%.0f°C", tempHour)
            tvWindVelocity.text = "Kecepatan Angin ${detail.wind_speed} km/jam"
            tvHumidity.text = "Kelembapan ${detail.humidity}%"
            tvPressure.text = "Tekanan ${detail.pressure} hPa"


            when (detail.weather[0].description) {
                "broken clouds" -> {
                    iconTemp.setAnimation(R.raw.broken_clouds)
                    tvWeather.text = "Awan Tersebar"
                }
                "light rain" -> {
                    iconTemp.setAnimation(R.raw.light_rain)
                    tvWeather.text = "Gerimis"
                }
                "haze" -> {
                    iconTemp.setAnimation(R.raw.broken_clouds)
                    tvWeather.text = "Berkabut"
                }
                "overcast clouds" -> {
                    iconTemp.setAnimation(R.raw.overcast_clouds)
                    tvWeather.text = "Awan Mendung"
                }
                "moderate rain" -> {
                    iconTemp.setAnimation(R.raw.moderate_rain)
                    tvWeather.text = "Hujan Ringan"
                }
                "few clouds" -> {
                    iconTemp.setAnimation(R.raw.few_clouds)
                    tvWeather.text = "Berawan"
                }
                "heavy intensity rain" -> {
                    iconTemp.setAnimation(R.raw.heavy_intentsity)
                    tvWeather.text = "Hujan Lebat"
                }
                "clear sky" -> {
                    iconTemp.setAnimation(R.raw.clear_sky)
                    tvWeather.text = "Cerah"
                }
                "scattered clouds" -> {
                    iconTemp.setAnimation(R.raw.scattered_clouds)
                    tvWeather.text = "Awan Tersebar"
                }
                else -> {
                    iconTemp.setAnimation(R.raw.unknown)
                    tvWeather.text = detail.weather[0].main
                }
            }
        }


    }
}