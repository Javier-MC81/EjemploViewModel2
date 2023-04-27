package com.jmoreno.ejemploviewmodel2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.jmoreno.ejemploviewmodel2.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: ViewModelMainActivity by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
        initCollects()
        Log.w("Tag", "Fin del onCreate")
    }


    private fun initListener() {
        binding.bStart.setOnClickListener {
            viewModel.start(10)
        }
    }
    private fun initCollects() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                Log.w("Tag", "Valor actualizado")
                when(it) {
                    is ViewModelMainActivity.UiResponse.Number -> binding.tvNumber.text = it.num.toString()
                    is ViewModelMainActivity.UiResponse.Ended -> binding.tvNumber.text = getString(R.string.finished)
                    is ViewModelMainActivity.UiResponse.Started -> binding.tvNumber.text = getString(R.string.ready_to_start)
                }

            }
        }
    }
}