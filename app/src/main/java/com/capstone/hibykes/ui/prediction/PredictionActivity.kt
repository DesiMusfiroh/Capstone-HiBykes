package com.capstone.hibykes.ui.prediction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.capstone.hibykes.R
import com.capstone.hibykes.data.local.entity.PredictionEntity
import com.capstone.hibykes.databinding.ActivityPredictionBinding
import com.capstone.hibykes.viewmodel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PredictionActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val EXTRA_PREDICTION = "extra_prediction"
    }
    private lateinit var binding: ActivityPredictionBinding
    private lateinit var viewModel: PredictionViewModel
    private lateinit var prediction: PredictionEntity
    private var statusBookmark = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[PredictionViewModel::class.java]

        prediction = intent.getParcelableExtra(EXTRA_PREDICTION)!!
        populatePrediction()
        checkBookmark()
        binding.btnSaveBookmark.setOnClickListener(this)
        binding.btnRemoveBookmark.setOnClickListener(this)
    }

    private fun populatePrediction() {
        binding.apply {
            tvDatetime.text = prediction.datetime
        }
    }
    private fun checkBookmark() {
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkBookmark(prediction.id)
            withContext(Dispatchers.Main) {
                setBookmarkState(count)
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_save_bookmark -> {
                viewModel.insertBookmark(prediction)
                Toast.makeText(baseContext, "Added prediction to bookmark time", Toast.LENGTH_SHORT).show()
                checkBookmark()
            }
            R.id.btn_remove_bookmark -> {
                viewModel.deleteFromBookmark(prediction.id)
                Toast.makeText(baseContext, "Removed prediction from bookmark time", Toast.LENGTH_SHORT).show()
                checkBookmark()
            }
        }
    }

    private fun setBookmarkState(count : Int?) {
        if (count != null ) {
            if (count > 0) {
                binding.btnSaveBookmark.visibility = GONE
                binding.btnRemoveBookmark.visibility = VISIBLE
                statusBookmark = true
            } else {
                binding.btnSaveBookmark.visibility = VISIBLE
                binding.btnRemoveBookmark.visibility = GONE
                statusBookmark = false
            }
        }
    }
}