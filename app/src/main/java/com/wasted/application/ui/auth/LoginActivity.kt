package com.wasted.application.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.wasted.application.R
import com.wasted.application.backend.AuthService
import com.wasted.application.utils.WastedApplication

class LoginActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 0

    override fun onStart() {
        super.onStart()

        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            findViewById<TextView>(R.id.loginTextView).text = account.displayName
        } else {
            findViewById<TextView>(R.id.loginTextView).text = resources.getString(R.string.logged_in)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<SignInButton>(R.id.sign_in_button).setOnClickListener {
            val signInIntent = (application as WastedApplication).mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
        // todo send to backend
        AuthService.updateCurrentUser(account)
        updateUI(account)
    }
}
