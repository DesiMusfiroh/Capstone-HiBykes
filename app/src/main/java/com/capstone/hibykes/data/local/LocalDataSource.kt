package com.capstone.hibykes.data.local

import com.capstone.hibykes.data.local.room.HiBykesDao

class LocalDataSource {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(): LocalDataSource =
            INSTANCE ?: LocalDataSource()
    }
}