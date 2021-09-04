package com.nxt.weatherfpt.model

data class CurrentWeather(
    val weather: ArrayList<Weather>,
    val sys: Sys,
    val main: Main,
    val name: String,
    val wind: Wind
)
data class Weather(val description: String, val icon: String)
data class Main(val temp: Double, val humidity: Int)
data class Sys(val sunrise: Int, val sunset: Int)
data class Wind(val speed:Double)