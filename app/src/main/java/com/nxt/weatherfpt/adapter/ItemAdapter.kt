package com.nxt.weatherfpt.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nxt.weatherfpt.R
import com.nxt.weatherfpt.model.ListDayWeather
import kotlinx.android.synthetic.main.item_layout.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ItemAdapter( var listWeather: ArrayList<ListDayWeather>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false))
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val time = listWeather[position].dt.toString()

        val l: Long =(time.toLong())

        val date = Date(l * 1000L)

        val simpleDateFormat = SimpleDateFormat("EE\nHH-mm")

        val days = simpleDateFormat.format(date)

        holder.itemView.tv_day.text = days.toString()

        val icon = listWeather[position].weather[0].icon

        Glide.with(holder.itemView.context).load("https://openweathermap.org/img/w/$icon.png")
            .into(holder.itemView.img_day)
        holder.itemView.temp_min.text = listWeather[position].main.tempMin.toInt().toString()
        holder.itemView.temp_max.text = listWeather[position].main.tempMax.toInt().toString()
    }

    override fun getItemCount(): Int {
        return listWeather.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
}