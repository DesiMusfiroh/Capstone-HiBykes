package com.capstone.hibykes.ui.maps

import androidx.lifecycle.ViewModel
import com.capstone.hibykes.data.local.entity.StationEntity
import com.capstone.hibykes.utils.DataDummy

class MapsViewModel: ViewModel() {
    fun getStations(): List<StationEntity> = DataDummy.generateDataStation()
}