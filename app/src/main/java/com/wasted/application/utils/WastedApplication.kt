package com.wasted.application.utils

import android.app.Application
import android.content.Intent
import com.facebook.appevents.AppEventsLogger
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.wasted.application.R
import com.wasted.application.backend.AuthService
import com.wasted.application.ui.ActivityStats
import com.wasted.application.ui.auth.LoginActivity

class WastedApplication : Application() {
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate() {
        super.onCreate()
        AppEventsLogger.activateApp(this)

        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestServerAuthCode(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        AuthService.updateCurrentUser(GoogleSignIn.getLastSignedInAccount(this))
        subscribeToAuth()
    }

    private fun subscribeToAuth() {
        AuthService.currentUser.observeForever {
            if (it != null) {
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