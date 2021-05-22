package com.capstone.hibykes.ui.station

import androidx.lifecycle.ViewModel
import com.capstone.hibykes.data.local.StationEntity
import com.capstone.hibykes.utils.DataDummy

class StationViewModel : ViewModel() {
    fun getStations(): List<StationEntity> = DataDummy.generateDataStation()
}