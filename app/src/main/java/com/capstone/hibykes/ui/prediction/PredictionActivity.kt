package com.capstone.hibykes.ui.prediction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.hibykes.R

class PredictionActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PREDICTION = "extra_prediction"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prediction)
    }
}