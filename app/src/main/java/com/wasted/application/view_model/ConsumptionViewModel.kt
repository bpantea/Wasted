package com.wasted.application.view_model

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wasted.application.backend.AuthService
import com.wasted.application.backend.ConsumptionService
import com.wasted.application.model.Stats
import com.wasted.application.model.ConsumptionDto
import com.wasted.application.utils.retrofit.RetrofitProvider
import kotlinx.coroutines.launch
import java.lang.Exception

class ConsumptionViewModel(application: Application) : AndroidViewModel(application) {

    val stats = MutableLiveData<Stats>(null)

    private val consumptionService = RetrofitProvider.createService(application, ConsumptionService::class.java)

    fun createConsumption(consumptionDto: ConsumptionDto) {
        viewModelScope.launch {
            consumptionService.createConsumption(consumptionDto)
        }
    }

    fun getStats() {
        viewModelScope.launch {
            try {
                stats.value = consumptionService.getStats(AuthService.idUser!!)
            } catch (ex: Exception) {
                stats
            }

        }
    }
}
