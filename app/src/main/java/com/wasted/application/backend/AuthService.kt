package com.wasted.application.backend

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

data class HelloWorld(val response: String) {
    override fun toString(): String {
        return response
    }
}

data class AuthByToken(val Authorization: String)

interface AuthService {
    @POST("oauth2/authorization/token")
    @FormUrlEncoded
    fun send(@Field("Authorization") idToken: String): Call<Any>

    @GET("api/user/unauthenticated")
    fun helloWorld(): Call<HelloWorld>
}
