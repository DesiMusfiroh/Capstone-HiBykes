package com.capstone.hibykes.utils

import com.capstone.hibykes.data.local.entity.PredictionEntity
import com.capstone.hibykes.data.local.entity.StationEntity

object DataDummy {
    fun generateDataStation(): List<StationEntity> {
        val stations = ArrayList<StationEntity>()
        stations.add( StationEntity(1, "Bike Station Kayu Putih", "Seberang Bella Terra", "Jl. Perintis Kemerdekaan KM.16, RW.16, Kayu Putih, Pulo Gadung, East Jakarta City",-6.176089, 106.893281, "https://picsum.photos/200/300"))
        stations.add( StationEntity(2, "Gates CS building", "Many long nights in this basement", "Jln.",37.430, -122.173, "https://picsum.photos/200/300"))
        stations.add( StationEntity(3, "Pinkberry", "First date with my wife", "Jln.",37.444, -122.170, "https://picsum.photos/200/300"))
        stations.add( StationEntity(4, "Althea", "Chicago upscale dining with an amazing view","Jln.", 41.895, -87.625, "https://picsum.photos/200/300"))
        stations.add( StationEntity(5, "Citizen Eatery", "Bright cafe in Austin with a pink rabbit", "Jln.",30.322, -97.739, "https://picsum.photos/200/300"))
        stations.add( StationEntity(6, "Kati Thai", "Authentic Portland Thai food, served with love", "Jln.",45.505, -122.635, "https://picsum.photos/200/300"))
        return stations
    }

    fun generateDataPrediction(): List<PredictionEntity> {
        val predictions = ArrayList<PredictionEntity>()
        predictions.add(PredictionEntity(1, 1, "1 June", 24, "low demand"))
        predictions.add(PredictionEntity(2, 1, "2 June", 33, "high demand"))
        predictions.add(PredictionEntity(3, 1, "3 June", 13, "low demand"))
        predictions.add(PredictionEntity(4, 1, "4 June", 39, "high demand"))
        predictions.add(PredictionEntity(5, 1, "5 June", 46, "high demand"))
        predictions.add(PredictionEntity(6, 2, "1 June", 18, "low demand"))
        predictions.add(PredictionEntity(7, 2, "2 June", 22, "high demand"))
        predictions.add(PredictionEntity(8, 2, "3 June", 20, "high demand"))
        predictions.add(PredictionEntity(9, 2, "4 June", 50, "high demand"))
        return predictions
    }
}