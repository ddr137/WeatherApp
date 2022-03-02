package com.ddr1.weatherapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ddr1.weatherapp.R
import com.ddr1.weatherapp.adapter.WeatherAdapter
import com.ddr1.weatherapp.databinding.ActivityMainBinding
import com.ddr1.weatherapp.model.WeatherModel
import com.ddr1.weatherapp.utils.CustomProgressDialog
import com.ddr1.weatherapp.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module
import timber.log.Timber
import java.util.*

val mainModule = module {
    factory { MainActivity() }
}

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: WeatherViewModel by viewModel()
    private var daily: String? = null
    private var location: String = ""
    private var lat: Double? = null
    private var lng: Double? = null
    private val progressDialog = CustomProgressDialog()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getObserve()
    }

    private fun getObserve(){
        val dateNow = Calendar.getInstance().time
        daily = DateFormat.format("EEEE", dateNow) as String
        viewModel.fetchWeather()
        viewModel.loading.observe(this) {
            if (it){
                progressDialog.show(this)
            } else {
                progressDialog.dialog.cancel()
                progressDialog.dialog.dismiss()
            }

        }
        viewModel.weather.observe(this) {
            Timber.d("articleSize: ${it.daily[0]}")
            binding.rvWeather.adapter = weatherAdapter
            weatherAdapter.add(it.daily)

            val format = Calendar.getInstance().time
            val date = DateFormat.format("d MMMM yyyy", format) as String
            val formatDate = "$daily, $date"
            binding.tvDateTime.text = formatDate

            location = it.timezone
            binding.currentLocation.text = "Bandung"
            binding.tvTemperature.text = String.format(Locale.getDefault(), "%.0f°C", it.current.temp)
            binding.tvWindVelocity.text = "Kecepatan Angin ${it.current.wind_speed} km/jam"
            binding.tvHumidity.text = "Kelembapan ${it.current.humidity}%"
            binding.tvPressure.text = "Tekanan ${it.current.pressure} hPa"
            when (it.current.weather[0].description) {
                "broken clouds" -> {
                    binding.iconTemp.setAnimation(R.raw.broken_clouds)
                    binding.tvWeather.text = "Awan Tersebar"
                }
                "light rain" -> {
                    binding.iconTemp.setAnimation(R.raw.light_rain)
                    binding.tvWeather.text = "Gerimis"
                }
                "haze" -> {
                    binding.iconTemp.setAnimation(R.raw.broken_clouds)
                    binding.tvWeather.text = "Berkabut"
                }
                "overcast clouds" -> {
                    binding.iconTemp.setAnimation(R.raw.overcast_clouds)
                    binding.tvWeather.text = "Awan Mendung"
                }
                "moderate rain" -> {
                    binding.iconTemp.setAnimation(R.raw.moderate_rain)
                    binding.tvWeather.text = "Hujan Ringan"
                }
                "few clouds" -> {
                    binding.iconTemp.setAnimation(R.raw.few_clouds)
                    binding.tvWeather.text = "Berawan"
                }
                "heavy intensity rain" -> {
                    binding.iconTemp.setAnimation(R.raw.heavy_intentsity)
                    binding.tvWeather.text = "Hujan Lebat"
                }
                "clear sky" -> {
                    binding.iconTemp.setAnimation(R.raw.clear_sky)
                    binding.tvWeather.text = "Cerah"
                }
                "scattered clouds" -> {
                    binding.iconTemp.setAnimation(R.raw.scattered_clouds)
                    binding.tvWeather.text = "Awan Tersebar"
                }
                else -> {
                    binding.iconTemp.setAnimation(R.raw.unknown)
                    binding.tvWeather.text = it.current.weather[0].main
                }
            }

        }
        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private val weatherAdapter by lazy {
        WeatherAdapter(arrayListOf(), object : WeatherAdapter.OnAdapterListener{
            override fun onClick(weatherModel: WeatherModel.Daily) {
                startActivity(
                    Intent(this@MainActivity, DetailWeatherActivity::class.java)
                        .putExtra("intent_detail", weatherModel)
                )
            }
        })
    }
}