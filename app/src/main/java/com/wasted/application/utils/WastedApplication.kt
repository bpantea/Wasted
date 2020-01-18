package com.wasted.application.utils

import android.app.Application
import android.content.Intent
import android.widget.Toast
import com.facebook.appevents.AppEventsLogger
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.wasted.application.backend.AuthService
import com.wasted.application.backend.StringResponse
import com.wasted.application.model.GoogleUserDto
import com.wasted.application.model.User
import com.wasted.application.ui.ActivityStats
import com.wasted.application.ui.auth.LoginActivity
import com.wasted.application.utils.retrofit.RetrofitProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WastedApplication : Application() {
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var authService: AuthService

    override fun onCreate() {
        super.onCreate()
        AppEventsLogger.activateApp(this)

        authService = RetrofitProvider.createService(this, AuthService::class.java)

        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        AuthService.updateAuthenticationUser(GoogleSignIn.getLastSignedInAccount(this))
        subscribeToAuth()
    }

    private fun subscribeToAuth() {
        AuthService.authenticationGoogleUser.observeForever {
            if (it != null) {
                authService.login(GoogleUserDto(it.id!!, it.displayName!!, it.email, it.photoUrl.toString()))
                    .enqueue(object : Callback<User> {
                        override fun onFailure(call: Call<User>?, t: Throwable?) {

                        }

                        override fun onResponse(call: Call<User>?, response: Response<User>?) {
                            AuthService.updateFullUser(response!!.body())
                        }
                    })
                val intent = Intent(this, ActivityStats::class.java)
                intent.flags += Intent.FLAG_ACTIVITY_NO_HISTORY + Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags += Intent.FLAG_ACTIVITY_NO_HISTORY + Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
    }
}