package com.wasted.application.backend

import com.wasted.application.model.ConsumptionDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

private const val consumptionApi = "api/consumption"

interface ConsumptionService {
    @POST(consumptionApi)
    suspend fun createConsumption(consumptionDto: ConsumptionDto)
    @GET("$consumptionApi/stats/{idUser}")
    suspend fun getStatsForUser(@Path("idUser") userid:String)
}