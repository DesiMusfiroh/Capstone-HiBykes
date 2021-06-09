package com.capstone.hibykes.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListPredictionResponse(
    @SerializedName("prediction")
    val results: MutableList<PredictionResponse>
)
