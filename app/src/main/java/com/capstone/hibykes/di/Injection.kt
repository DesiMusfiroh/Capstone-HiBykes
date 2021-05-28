package com.capstone.hibykes.di

import android.content.Context
import com.capstone.hibykes.data.HiBykesRepositories
import com.capstone.hibykes.data.local.LocalDataSource
import com.capstone.hibykes.data.remote.RemoteDataSource

object Injection {
    fun provideRepository(context: Context): HiBykesRepositories {
        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance()
        return HiBykesRepositories.getInstance(remoteDataSource, localDataSource)
    }
}