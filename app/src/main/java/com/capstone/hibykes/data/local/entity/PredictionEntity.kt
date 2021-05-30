package com.capstone.hibykes.data.local.entity

import java.io.Serializable

data class PredictionEntity (
    val id: Int,
    val stationId: Int,
    val datetime: String,
    val demandCount: Int,
    val desc: String,
//    val weather: String,
//    val temperature: Double,
//    val humidity: Double,
//    val wind: Double
) : Serializable