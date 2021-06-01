package com.capstone.hibykes.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.hibykes.R
import com.capstone.hibykes.data.local.entity.StationEntity
import com.capstone.hibykes.databinding.FragmentHomeBinding
import com.capstone.hibykes.ui.station.StationActivity
import com.capstone.hibykes.viewmodel.ViewModelFactory
import java.lang.StringBuilder

class HomeFragment : Fragment(){

    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private lateinit var stationAdapter: StationAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        getWeatherData()
        getAirPollution()
        getStations()
    }

    private fun getStations() {
        viewModel.getStationsData().observe(viewLifecycleOwner, {
            stationAdapter = StationAdapter(it)
            stationAdapter.notifyDataSetChanged()

            fragmentHomeBinding.apply {
                rvStation.layoutManager = LinearLayoutManager(context)
                rvStation.setHasFixedSize(true)
                rvStation.adapter = stationAdapter
            }
            stationAdapter.setOnItemClickCallback(object : StationAdapter.OnItemClickCallback {
                override fun onItemClicked(data: StationEntity) {
                    Log.d("station", "station === $data")
                    val intent =  Intent(context, StationActivity::class.java)
                    intent.putExtra(StationActivity.EXTRA_STATION, data)
                    startActivity(intent)
                }
            })
        })
    }

    private fun getWeatherData() {
        viewModel.getCurrentWeather("Jambi").observe(viewLifecycleOwner, { data ->
            if (data != null) {
                fragmentHomeBinding.apply {
                    tvCityName.text = data.name.toString()
                    Glide.with(requireContext())
                        .load("https://openweathermap.org/img/wn/" + (data.weather?.get(0)?.icon) + "@2x.png").centerCrop()
                        .into(imgWeatherPictures)
                    tvTemperature.text = StringBuilder(data.main?.temp.toString() + "°C")
                    tvWeather.text = data.weather?.get(0)?.description.toString()
                }
            }
        })
    }

    private fun getAirPollution() {
        viewModel.getAirPollution(50.0, 70.0).observe(viewLifecycleOwner, { data ->
            Log.d("pollution", "AQI data = ${data.list?.get(0)?.main?.aqi}")
            if (data != null) {
                fragmentHomeBinding.apply {
                    val aqi = data.list?.get(0)?.main?.aqi
                    when (aqi) {
                        in 0..50 -> {
                            tvAqiIndex.text = StringBuilder("AQI : $aqi")
                            tvAqiLevel.text = StringBuilder("Good")
                            imgAqi.setImageResource(R.drawable.img_aqi_good)
                        }
                        in 51..100 -> {
                            tvAqiIndex.text = StringBuilder("AQI : $aqi")
                            tvAqiLevel.text = StringBuilder("Moderate")
                            imgAqi.setImageResource(R.drawable.img_aqi_moderate)
                        }
                        in 101..150 -> {
                            tvAqiIndex.text = StringBuilder("AQI : $aqi")
                            tvAqiLevel.text = StringBuilder("Unhealthy for sensitive")
                            imgAqi.setImageResource(R.drawable.img_aqi_sensitive)
                        }
                        in 151..200 -> {
                            tvAqiIndex.text = StringBuilder("AQI : $aqi")
                            tvAqiLevel.text = StringBuilder("Unhealthy")
                            imgAqi.setImageResource(R.drawable.img_aqi_unhealthy)
                        }
                        in 201..300 -> {
                            tvAqiIndex.text = StringBuilder("AQI : $aqi")
                            tvAqiLevel.text = StringBuilder("Very unhealthy")
                            imgAqi.setImageResource(R.drawable.img_aqi_very_unhealthy)
                        }
                        in 301..500 -> {
                            tvAqiIndex.text = StringBuilder("AQI : $aqi")
                            tvAqiLevel.text = StringBuilder("Hazardous")
                            imgAqi.setImageResource(R.drawable.img_aqi_hazardous)
                        }
                    }
                }
            }
        })
    }
}