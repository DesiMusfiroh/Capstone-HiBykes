package com.capstone.hibykes.data.local

class LocalDataSource {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(): LocalDataSource =
            INSTANCE ?: LocalDataSource()
    }
}