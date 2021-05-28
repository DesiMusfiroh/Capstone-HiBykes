package com.capstone.hibykes.data

import androidx.lifecycle.LiveData
import com.capstone.hibykes.data.local.LocalDataSource
import com.capstone.hibykes.data.remote.RemoteDataSource
import com.capstone.hibykes.data.remote.response.AirPollutionResponse
import com.capstone.hibykes.data.remote.response.WeatherResponse

class HiBykesRepositories private constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource
    ) {
    companion object {
        @Volatile
        private var instance: HiBykesRepositories? = null
        fun getInstance(remoteData: RemoteDataSource, localData: LocalDataSource): HiBykesRepositories =
            instance ?: synchronized(this) {
                instance ?: HiBykesRepositories(remoteData, localData).apply { instance = this }
            }
    }

    fun getCurrentWeather(city: String): LiveData<WeatherResponse> {
        return remoteDataSource.getCurrentWeather(city)
    }

    fun getAirPollution(lat: Double, lon: Double): LiveData<AirPollutionResponse> {
        return remoteDataSource.getCurrentAirPollution(lat, lon)
    }
}