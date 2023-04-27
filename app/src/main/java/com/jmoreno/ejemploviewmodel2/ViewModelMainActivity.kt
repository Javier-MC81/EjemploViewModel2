package com.jmoreno.ejemploviewmodel2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelMainActivity: ViewModel() {

    private val _uiState = MutableStateFlow<UiResponse>(UiResponse.Started())
    val uiState: StateFlow<UiResponse> = _uiState

    var contador = 0
    fun start(repetitions: Int) {

        viewModelScope.launch {
            repeat(repetitions) {
                delay(1000)
                contador++
                _uiState.value = UiResponse.Number(contador)
            }
            //Notificar que he terminado
            _uiState.value = UiResponse.Ended()
        }
    }
    sealed class UiResponse {
        data class Number(var num: Int): UiResponse()
        class Ended(): UiResponse()
        class Started() : UiResponse()
    }



}