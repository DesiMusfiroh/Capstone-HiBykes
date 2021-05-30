package com.capstone.hibykes.ui.station

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.core.cartesian.series.Column
import com.anychart.enums.Anchor
import com.anychart.enums.HoverMode
import com.anychart.enums.Position
import com.anychart.enums.TooltipPositionMode
import com.capstone.hibykes.data.local.entity.StationEntity
import com.capstone.hibykes.databinding.ActivityStationBinding
import com.capstone.hibykes.viewmodel.ViewModelFactory

class StationActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_STATION = "extra_station"
    }
    private lateinit var binding: ActivityStationBinding
    private lateinit var viewModel: StationViewModel
    private lateinit var station: StationEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[StationViewModel::class.java]

        station = intent.getParcelableExtra<StationEntity>(EXTRA_STATION) as StationEntity
        populateStation()
        predictionChart()
    }

    private fun populateStation() {
        binding.apply {
            tvStationName.text = station.name
            tvStationAddress.text = station.address
        }
    }

    private fun predictionChart() {
        val anyChartView = binding.anyChartView
        anyChartView.setProgressBar(binding.progressBar)
        val cartesian = AnyChart.column()

        val predictionData = viewModel.getPredictionData().filter { it.stationId == station.id }
        val chartData = predictionData.map {
            ValueDataEntry(it.datetime, it.demandCount)
        }

        val column: Column = cartesian.column(chartData)
        column.tooltip()
            .titleFormat("{%X}")
            .position(Position.CENTER_BOTTOM)
            .anchor(Anchor.CENTER_BOTTOM)
            .offsetX(0.0)
            .offsetY(5.0)
            .format("{%Value}{groupsSeparator: }")

        cartesian.animation(true)
        cartesian.yScale().minimum(0.0)
        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }")
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.interactivity().hoverMode(HoverMode.BY_X)
        anyChartView.setChart(cartesian)
    }
}