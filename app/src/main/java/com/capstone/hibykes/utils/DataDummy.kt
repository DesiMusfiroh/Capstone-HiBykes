package com.capstone.hibykes.utils

import com.capstone.hibykes.data.local.entity.StationEntity

object DataDummy {
    fun generateDataStation(): List<StationEntity> {
        val stations = ArrayList<StationEntity>()
        stations.add( StationEntity("Bike Station Kayu Putih", "Seberang Bella Terra", "Jl. Perintis Kemerdekaan KM.16, RW.16, Kayu Putih, Pulo Gadung, East Jakarta City",-6.176089, 106.893281, "https://picsum.photos/200/300"))
        stations.add( StationEntity("Gates CS building", "Many long nights in this basement", "Jln.",37.430, -122.173, "https://picsum.photos/200/300"))
        stations.add( StationEntity("Pinkberry", "First date with my wife", "Jln.",37.444, -122.170, "https://picsum.photos/200/300"))
        stations.add( StationEntity("Althea", "Chicago upscale dining with an amazing view","Jln.", 41.895, -87.625, "https://picsum.photos/200/300"))
        stations.add( StationEntity("Citizen Eatery", "Bright cafe in Austin with a pink rabbit", "Jln.",30.322, -97.739, "https://picsum.photos/200/300"))
        stations.add( StationEntity("Kati Thai", "Authentic Portland Thai food, served with love", "Jln.",45.505, -122.635, "https://picsum.photos/200/300"))
        return stations
    }
}