package com.ddr1.weatherapp.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ddr1.weatherapp.R
import com.ddr1.weatherapp.databinding.ItemWeatherBinding
import com.ddr1.weatherapp.model.WeatherModel
import java.text.SimpleDateFormat
import java.time.Instant.ofEpochSecond
import java.time.format.DateTimeFormatter
import java.util.*


class WeatherAdapter(
    var weathers: ArrayList<WeatherModel.Daily>,
    var listener: OnAdapterListener,
): RecyclerView.Adapter<WeatherAdapter.ViewHolder>(){

    class ViewHolder(val binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(weatherModel: WeatherModel.Daily) {

            val c = Calendar.getInstance()
            val timeOfDay = c[Calendar.HOUR_OF_DAY]
            var tempHour = 0.0

            when (timeOfDay) {
                in 0..11 -> {
                    tempHour = weatherModel.temp.morn
                }
                in 12..15 -> {
                    tempHour = weatherModel.temp.day
                }
                in 16..20 -> {
                    tempHour = weatherModel.temp.eve
                }
                in 21..23 -> {
                    tempHour = weatherModel.temp.night
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val date = DateTimeFormatter.ISO_INSTANT.format(ofEpochSecond(weatherModel.dt.toLong()))
                val currentFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                val dateParse = currentFormat.parse(date)
                val day = DateFormat.format("EEEE", dateParse) as String
                binding.tvDay.text = day.format(dateParse)
            }

            binding.tvTemperature.text = String.format(Locale.getDefault(), "%.0f°C", tempHour)

            when (weatherModel.weather[0].description) {
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
                    binding.tvWeather.text = weatherModel.weather[adapterPosition].main
                }
            }

        }
    }

    interface OnAdapterListener {
        fun onClick(weatherModel: WeatherModel.Daily)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemWeatherBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather = weathers[position]
        holder.bind(weather)
        holder.itemView.setOnClickListener {
            listener.onClick(weather)
        }
    }

    override fun getItemCount() = weathers.size

    @SuppressLint("NotifyDataSetChanged")
    fun add(data: List<WeatherModel.Daily>) {
        weathers.clear()
        weathers.addAll(data)
        notifyDataSetChanged()
        //notifyItemRangeInserted((weathers.size - data.size), data.size )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear(){
        weathers.clear()
        notifyDataSetChanged()
    }

}