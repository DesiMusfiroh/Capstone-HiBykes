package com.capstone.hibykes.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.hibykes.data.remote.api.WeatherApiConfig
import com.capstone.hibykes.data.remote.response.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    companion object {
        fun getInstance(): RemoteDataSource {
            return RemoteDataSource()
        }
        private const val TAG = "RemoteDataSource"
    }

    fun getCurrentWeather(city: String): LiveData<WeatherResponse> {
        val weather: MutableLiveData<WeatherResponse> = MutableLiveData()
        val client = WeatherApiConfig.getApiService().getData(city)

        client.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    weather.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
        return weather
    }
}