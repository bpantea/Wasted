package com.wasted.application.backend

import com.wasted.application.model.Drink
import retrofit2.http.*

private const val drinkApi = "api/drink"

interface DrinkService {
    @GET("$drinkApi/getOne/{id}")
    suspend fun getDrink(@Path("id") id: String): Drink?

    @GET("$drinkApi/all")
    suspend fun getAllDrinks(): List<Drink>

    @POST(drinkApi)
    suspend fun addDrink(@Body drink: Drink): Drink

    @DELETE("${drinkApi}{id}")
    suspend fun deleteDrink(@Path("id") id: String)
}