package com.wasted.application.model

data class ConsumptionDto(val id: Long,
                          val userId: String,
                          val drinkId: String,
                          val quantity: Double)
