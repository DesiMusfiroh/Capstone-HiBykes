package com.capstone.hibykes.ui.station

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.hibykes.data.local.StationEntity
import com.capstone.hibykes.databinding.FragmentStationBinding

class StationFragment : Fragment() {
    private lateinit var fragmentStationBinding: FragmentStationBinding
    private lateinit var stationAdapter: StationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentStationBinding = FragmentStationBinding.inflate(layoutInflater, container, false)
        return fragmentStationBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[StationViewModel::class.java]
        val stations = viewModel.getStations()

        stationAdapter = StationAdapter(stations)
        stationAdapter.notifyDataSetChanged()

        fragmentStationBinding.apply {
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
}