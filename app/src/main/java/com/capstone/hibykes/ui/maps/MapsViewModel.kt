package com.capstone.hibykes.ui.maps

import androidx.lifecycle.ViewModel
import com.capstone.hibykes.data.local.StationEntity
import com.capstone.hibykes.utils.DataDummy

class MapsViewModel: ViewModel() {
    fun getStations(): List<StationEntity> = DataDummy.generateDataStation()
}