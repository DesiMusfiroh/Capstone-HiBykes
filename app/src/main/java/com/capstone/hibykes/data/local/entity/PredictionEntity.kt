package com.capstone.hibykes.data.local.entity

import java.io.Serializable

data class PredictionEntity (
   val stationId: Int,
   val datetime: Int,
   val rentedCount: Int,
) : Serializable