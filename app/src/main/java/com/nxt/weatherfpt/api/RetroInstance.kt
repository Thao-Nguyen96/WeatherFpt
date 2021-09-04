package com.nxt.weatherfpt.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {
    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val API_KEY = "8aa948f39a95d3ad675cde059b15d016"

        fun getInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}