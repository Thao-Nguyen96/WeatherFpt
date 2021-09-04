package com.nxt.weatherfpt.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nxt.weatherfpt.api.RetroInstance
import com.nxt.weatherfpt.api.RetroService
import com.nxt.weatherfpt.model.CurrentWeather
import com.nxt.weatherfpt.model.DayWeather
import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val listWeatherCurrent: MutableLiveData<CurrentWeather> = MutableLiveData()
    private val listWeatherDay: MutableLiveData<DayWeather> = MutableLiveData()

    fun getListCurrentWeatherObServer(): MutableLiveData<CurrentWeather> {
        return listWeatherCurrent
    }

    fun getListDayWeatherObserver(): MutableLiveData<DayWeather> {
        return listWeatherDay
    }

    fun makeApiCall(country: String, units: String, keyId: String) {
        RetroInstance.getInstance().create(RetroService::class.java)
            .getDataFromApi(country, units, keyId)
            .enqueue(object : retrofit2.Callback<CurrentWeather> {
                override fun onResponse(
                    call: Call<CurrentWeather>,
                    response: Response<CurrentWeather>,
                ) {
                    if (response.isSuccessful) {
                        println(response)
                        listWeatherCurrent.postValue(response.body())
                    } else {
                        listWeatherCurrent.postValue(null)
                    }
                }

                override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                    listWeatherCurrent.postValue(null)
                }
            })
    }

    fun makeApiCallDay(country: String, units: String, time: Int, apiKey: String) {
        RetroInstance.getInstance().create(RetroService::class.java)
            .getDataFomApi2(country, units, time, apiKey)
            .enqueue(object : retrofit2.Callback<DayWeather> {
                override fun onResponse(
                    call: Call<DayWeather>,
                    response: Response<DayWeather>,
                ) {
                    if (response.isSuccessful) {
                        listWeatherDay.postValue(response.body())
                    } else {
                        listWeatherDay.postValue(null)
                    }
                }

                override fun onFailure(call: Call<DayWeather>, t: Throwable) {
                    listWeatherDay.postValue(null)
                }
            })
    }
}