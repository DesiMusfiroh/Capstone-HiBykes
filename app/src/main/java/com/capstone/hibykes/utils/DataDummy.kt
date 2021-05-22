package com.capstone.hibykes.utils

import com.capstone.hibykes.data.local.StationEntity

object DataDummy {
    fun generateDataStation(): List<StationEntity> {
        val stations = ArrayList<StationEntity>()
        stations.add( StationEntity("Branner Hall", "Best dorm at Stanford", 37.426, -122.163))
        stations.add( StationEntity("Gates CS building", "Many long nights in this basement", 37.430, -122.173))
        stations.add( StationEntity("Pinkberry", "First date with my wife", 37.444, -122.170))
        stations.add( StationEntity("Althea", "Chicago upscale dining with an amazing view", 41.895, -87.625))
        stations.add( StationEntity("Citizen Eatery", "Bright cafe in Austin with a pink rabbit", 30.322, -97.739))
        stations.add( StationEntity("Kati Thai", "Authentic Portland Thai food, served with love", 45.505, -122.635))
        return stations
    }
}