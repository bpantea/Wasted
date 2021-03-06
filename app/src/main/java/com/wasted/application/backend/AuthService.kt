package com.wasted.application.backend

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.wasted.application.model.GoogleUserDto
import com.wasted.application.model.User
import retrofit2.Call
import retrofit2.http.*

data class StringResponse(val response: String) {
    override fun toString(): String {
        return response
    }
}

interface AuthService {

    companion object {

        var idUser: String? = null

        val authenticationGoogleUser: MutableLiveData<GoogleSignInAccount?> = MutableLiveData()

        fun updateAuthenticationUser(account: GoogleSignInAccount?) {
            authenticationGoogleUser.value = account
        }

        fun updateFullUser(user: User?) {
            idUser = user?.id
        }
    }

    @POST("api/user/google-user")
    fun login(@Body googleUser: GoogleUserDto): Call<User>
}
