package com.capstone.hibykes.ui.home

import androidx.lifecycle.ViewModel
import com.capstone.hibykes.data.local.StationEntity
import com.capstone.hibykes.utils.DataDummy

class HomeViewModel : ViewModel() {
    fun getStations(): List<StationEntity> = DataDummy.generateDataStation()
}