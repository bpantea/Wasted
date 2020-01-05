package com.wasted.application.backend

import com.wasted.application.model.ConsumptionDto
import retrofit2.http.POST

private const val consumptionApi = "api/consumption"

interface ConsumptionService {
    @POST(consumptionApi)
    suspend fun createConsumption(consumptionDto: ConsumptionDto)
}