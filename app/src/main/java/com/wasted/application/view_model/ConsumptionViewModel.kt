package com.wasted.application.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.wasted.application.backend.ConsumptionService
import com.wasted.application.model.ConsumptionDto
import com.wasted.application.utils.retrofit.RetrofitProvider
import kotlinx.coroutines.launch

class ConsumptionViewModel(application: Application) : AndroidViewModel(application) {

    private val consumptionService = RetrofitProvider.createService(application, ConsumptionService::class.java)

    fun createConsumption(consumptionDto: ConsumptionDto) {
        viewModelScope.launch {
            consumptionService.createConsumption(consumptionDto)
        }
    }
}
