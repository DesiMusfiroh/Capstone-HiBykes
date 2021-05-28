package com.capstone.hibykes.data.local.entity

import java.io.Serializable

data class StationEntity (
    val name: String,
    val description: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val image: String,
) : Serializable