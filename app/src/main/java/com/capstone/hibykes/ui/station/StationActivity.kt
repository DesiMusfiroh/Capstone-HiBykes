package com.capstone.hibykes.ui.station

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.hibykes.R
import com.capstone.hibykes.databinding.ActivityStationBinding
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate

class StationActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_STATION = "extra_station"
    }
    private lateinit var binding: ActivityStationBinding
    private lateinit var chartList: ArrayList<Entry>
    private lateinit var dataSet: LineDataSet
    lateinit var data: LineData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station)
        binding = ActivityStationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        chartList = ArrayList()
        chartList.add(Entry(10f, 500f))
        chartList.add(Entry(20f, 300f))
        chartList.add(Entry(30f, 100f))
        chartList.add(Entry(40f, 600f))
        chartList.add(Entry(50f, 200f))
        chartList.add(Entry(60f, 500f))
        dataSet = LineDataSet(chartList, "bike sharing demand")
        data = LineData(dataSet)
        binding.chart.data = data

        dataSet.setColors(ColorTemplate.JOYFUL_COLORS, 250)
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 15f
    }
}