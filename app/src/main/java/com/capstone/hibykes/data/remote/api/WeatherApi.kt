package com.capstone.hibykes.data.remote.api

import com.capstone.hibykes.data.remote.response.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather?&units=metric&APPID=b79bc281c43f45a69cd831ebc73127d8")
    fun getData(
        @Query("q") cityName: String
    ): Call<WeatherResponse>
}