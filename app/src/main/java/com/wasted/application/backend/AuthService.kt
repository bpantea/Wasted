package com.wasted.application.backend

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.wasted.application.model.User
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

    companion object {
        val currentUser: MutableLiveData<User?> = MutableLiveData()

        fun updateCurrentUser(account: GoogleSignInAccount?) {
            if (account == null) {
                currentUser.value = null
            } else {
                val user = User(account.id!!, account.displayName!!, account.email, account.photoUrl?.toString(), null, null, null)
                currentUser.value = user
            }
        }
    }

    @POST("oauth2/authorization/token")
    @FormUrlEncoded
    fun send(@Field("Authorization") idToken: String): Call<Any>

    @GET("api/user/unauthenticated")
    fun helloWorld(): Call<HelloWorld>
}
