package com.nxt.weatherfpt.api

import com.nxt.weatherfpt.model.CurrentWeather
import com.nxt.weatherfpt.model.DayWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {

    @GET("weather")
    fun getDataFromApi(
        @Query("q") query: String,
        @Query("units") units: String,
        @Query("appid") appid: String,
    ):Call<CurrentWeather>



    @GET("forecast")
    fun getDataFomApi2(
        @Query("q") query: String,
        @Query("units") units: String,
        @Query("cnt") time: Int,
        @Query("appid") appid: String
    ):Call<DayWeather>

}