package com.capstone.hibykes.data.local

import java.io.Serializable

data class StationEntity (
    val name: String,
    val description: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
) : Serializable