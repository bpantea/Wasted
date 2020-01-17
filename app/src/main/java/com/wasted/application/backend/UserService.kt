package com.wasted.application.backend

import com.wasted.application.model.ExtraFieldsUser
import com.wasted.application.model.User
import retrofit2.http.*

private const val userApi = "api/user"

interface UserService {
    @PUT("$userApi/extra-fields/{id}")
    suspend fun addExtraFieldsToUser(@Path("id") userId: String, @Body extraFieldsUser: ExtraFieldsUser): User

    @GET("$userApi/get/{id}")
    suspend fun getFullUser(@Path("id") userId: String): User
}