package com.capstone.hibykes.ui.home

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
        val stations = viewModel.getStations()

        stationAdapter = StationAdapter(stations)
        stationAdapter.notifyDataSetChanged()

        fragmentHomeBinding.apply {
            rvStation.layoutManager = LinearLayoutManager(context)
            rvStation.setHasFixedSize(true)
            rvStation.adapter = stationAdapter
        }
        stationAdapter.setOnItemClickCallback(object : StationAdapter.OnItemClickCallback {
            override fun onItemClicked(data: StationEntity) {
                Log.d("station", data.name)
            }
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
                    tvHumidity.text = StringBuilder(data.main?.humidity.toString() + "%")
                    tvWindSpeed.text = StringBuilder(data.wind?.speed.toString() + "m/s")
                }
            }
        })
    }

    private fun getAirPollution() {
        viewModel.getAirPollution(50.0, 79.0).observe(viewLifecycleOwner, { data ->
            if (data != null) {
                fragmentHomeBinding.apply {
                    val aqi = data.list?.get(0)?.main?.aqi
                    when (aqi) {
                        in 0..50 -> tvAqi.text = StringBuilder("Good")
                        in 51..100 -> tvAqi.text = StringBuilder("Average")
                        in 101..150 -> tvAqi.text = StringBuilder("Unhealthy")
                        in 151..200 -> tvAqi.text = StringBuilder("Very Unhealthy")
                        in 201..500 -> tvAqi.text = StringBuilder("Hazardous")
                    }
                }
            }
        })
    }
}