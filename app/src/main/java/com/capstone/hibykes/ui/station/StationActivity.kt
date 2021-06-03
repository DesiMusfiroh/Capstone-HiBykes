package com.capstone.hibykes.ui.station

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.core.cartesian.series.Column
import com.anychart.enums.Anchor
import com.anychart.enums.HoverMode
import com.anychart.enums.Position
import com.anychart.enums.TooltipPositionMode
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.capstone.hibykes.R
import com.capstone.hibykes.data.local.entity.PredictionEntity
import com.capstone.hibykes.data.local.entity.StationEntity
import com.capstone.hibykes.databinding.ActivityStationBinding
import com.capstone.hibykes.ui.prediction.PredictionActivity
import com.capstone.hibykes.ui.prediction.PredictionActivity.Companion.EXTRA_PREDICTION
import com.capstone.hibykes.viewmodel.ViewModelFactory

class StationActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_STATION = "extra_station"
        const val EXTRA_STATION_NAME = "extra_station_name"
    }
    private lateinit var binding: ActivityStationBinding
    private lateinit var viewModel: StationViewModel
    private lateinit var station: StationEntity
    private lateinit var predictionData: List<PredictionEntity>
    private lateinit var predictionAdapter: PredictionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[StationViewModel::class.java]

        station = intent.getParcelableExtra<StationEntity>(EXTRA_STATION)!!

//        val extras = intent.extras
//        if (extras != null) {
//            val stationName = extras.getInt(EXTRA_STATION_NAME)
//            viewModel.
//            viewModel.getMovie.observe(this, { movie ->
//                if (movie != null) {
//                    when (movie.status) {
//                        Status.LOADING -> Toast.makeText(applicationContext, "Loading", Toast.LENGTH_SHORT).show()
//                        Status.SUCCESS -> if (movie.data != null) {
//                            populateMovie(movie.data)
//                        }
//                        Status.ERROR -> Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            })
//        }
        predictionData = viewModel.getPredictionData().filter { it.stationId == station.id?.toInt() }
        populateStation()
        predictionChart()
        getPredictions()
    }

    private fun populateStation() {
        binding.apply {
            tvStationName.text = station.name
            tvStationAddress.text = station.address
            Glide.with(baseContext)
                    .load(station.image)
                    .transform(RoundedCorners(20))
                    .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_loading)
                                    .error(R.drawable.ic_error))
                    .into(binding.imgBackdrop)
        }
    }

    private fun predictionChart() {
        val anyChartView = binding.anyChartView
        anyChartView.setProgressBar(binding.progressBar)
        val cartesian = AnyChart.column()

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

    private fun getPredictions() {
        predictionAdapter = PredictionAdapter(predictionData)
        predictionAdapter.notifyDataSetChanged()

        binding.apply {
            rvPrediction.layoutManager = LinearLayoutManager(this@StationActivity, LinearLayoutManager.HORIZONTAL, false)
            rvPrediction.setHasFixedSize(true)
            rvPrediction.adapter = predictionAdapter
        }
        predictionAdapter.setOnItemClickCallback(object : PredictionAdapter.OnItemClickCallback {
            override fun onItemClicked(data: PredictionEntity) {
                val intent =  Intent(this@StationActivity, PredictionActivity::class.java)
                intent.putExtra(EXTRA_PREDICTION, data)
                startActivity(intent)
            }
        })
    }
}