package com.nxt.weatherfpt.activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nxt.weatherfpt.R
import com.nxt.weatherfpt.model.CurrentWeather
import com.nxt.weatherfpt.model.DayWeather
import com.nxt.weatherfpt.api.RetroInstance.Companion.API_KEY
import com.nxt.weatherfpt.adapter.ItemAdapter
import com.nxt.weatherfpt.model.ListDayWeather
import com.nxt.weatherfpt.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var listWeather: ArrayList<ListDayWeather>? = ArrayList()
    private var adapter: ItemAdapter? = null
    private var mProgressBar: ProgressDialog? = null

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mProgressBar = ProgressDialog(this)
        mProgressBar!!.setMessage("please wait...")
        mProgressBar!!.show()

        swipeRefreshLayout.setColorSchemeColors(Color.DKGRAY)

        adapter = ItemAdapter(listWeather!!)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        rv.adapter = adapter

        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.makeApiCall("saigon", "metric", API_KEY)
        viewModel.makeApiCallDay("saigon", "metric", 18, API_KEY)

        viewModel.getListDayWeatherObserver()
            .observe(this, androidx.lifecycle.Observer<DayWeather> {
                if (it != null) {
                    adapter!!.listWeather = it.list
                    adapter!!.notifyDataSetChanged()
                }
            })

        viewModel.getListCurrentWeatherObServer()
            .observe(this, androidx.lifecycle.Observer<CurrentWeather> {
                if (it != null) {
                    tv_city.text = it.name
                    tv_description.text = it.weather[0].description

                    val icon = it.weather[0].icon
                    temp_current.text = it.main.temp.toInt().toString() + "°C"
                    tv_humidity.text = it.main.humidity.toString() + "%"
                    tv_wind.text = (it.wind.speed * 60).toInt().toString() + "mph"

                    Glide.with(this)
                        .load("https://openweathermap.org/img/w/$icon.png")
                        .into(img_current)

                    val sunrise: Long = it.sys.sunrise.toLong()
                    tv_sunrise.text =
                        SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise * 1000))

                    val sunset: Long = it.sys.sunset.toLong()
                    tv_sunset.text =
                        SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset * 1000))

                    mProgressBar!!.dismiss()

                } else {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                }
            })

        swipeRefreshLayout.setOnRefreshListener {

            viewModel.makeApiCall("saigon", "metric", API_KEY)
            viewModel.makeApiCallDay("saigon", "metric", 18, API_KEY)

            Handler().postDelayed({ swipeRefreshLayout.isRefreshing = false }, 3000)
        }

        val timer = object : CountDownTimer(1000 * 300, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                viewModel.makeApiCall("saigon", "metric", API_KEY)
                viewModel.makeApiCallDay("saigon", "metric", 18, API_KEY)
                start()
            }
        }
        timer.start()


//        GlobalScope.launch {
//            while (true) {
//                delay(300000)
//
//                viewModel.makeApiCall("london", "metric", API_KEY)
//                viewModel.makeApiCallDay("london", "metric", 14, API_KEY)
//            }
//        }
    }
}