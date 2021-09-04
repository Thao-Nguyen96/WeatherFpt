package com.nxt.weatherfpt.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DayWeather(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: ArrayList<ListDayWeather>
)

data class ListDayWeather(val dt: Int, val main: MainDay, val weather: ArrayList<WeatherInfo>)

data class WeatherInfo(val icon: String)

data class MainDay(
    @SerializedName("temp_min")
    @Expose
    val tempMin: Double,
    @SerializedName("temp_max")
    @Expose
    val tempMax: Double,
)