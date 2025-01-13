package com.example.road2gorillaapp.userinterface

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StoparicaViewModel : ViewModel() {
    var casovnik = mutableStateOf(0L)
        private set

    var poteka = mutableStateOf(false)
        private set

    private var job: Job? = null

    fun startStoparica() {
        if (poteka.value) return
        poteka.value = true
        job = viewModelScope.launch {
            while (poteka.value) {
                delay(1000L)
                casovnik.value++
            }
        }
    }

    fun pauseStoparica() {
        poteka.value = false
        job?.cancel()
    }

    fun resetStoparica() {
        poteka.value = false
        job?.cancel()
        casovnik.value = 0L
    }
}
