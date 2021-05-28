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
        val stations = viewModel.getStations()

//        viewModel.getCurrentWeather("jambi").observe(viewLifecycleOwner, { weather ->
//            if (weather != null) {
//                Log.d("weather", weather.clouds.toString())
//            }
//        })

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
            fragmentHomeBinding.apply {
                tvCityName.text = data.name.toString()
                Glide.with(requireContext())
                    .load("https://openweathermap.org/img/wn/" + (data.weather?.get(0)?.icon) + "@2x.png").centerCrop()
                    .into(imgWeatherPictures)

                tvTemperature.text = StringBuilder(data.main?.temp.toString() + "°C")
                tvHumidity.text = StringBuilder(data.main?.humidity.toString() + "%")
                tvWindSpeed.text = StringBuilder(data.wind?.speed.toString() + "m/s")
            }
        })
    }
}