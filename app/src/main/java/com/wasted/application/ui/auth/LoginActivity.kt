package com.wasted.application.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.squareup.okhttp.FormEncodingBuilder
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.wasted.application.R
import com.wasted.application.backend.AuthService
import com.wasted.application.backend.HelloWorld
import com.wasted.application.utils.retrofit.RetrofitProvider
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class LoginActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 0
    private val TAG = "LoginActivity"


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

        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
           // .requestIdToken(getString(R.string.server_client_id))
            //.requestServerAuthCode(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        findViewById<SignInButton>(R.id.sign_in_button).setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        findViewById<Button>(R.id.logoutButton).setOnClickListener {
            signOut()
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
//        account.serverAuthCode
//        val service = RetrofitProvider.createService(this, AuthService::class.java)
//        service.send(account.serverAuthCode!!).enqueue(object : Callback<Any> {
//            override fun onFailure(call: Call<Any>, t: Throwable) {
//                Toast.makeText(applicationContext, "Failed with ${t.message}", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(call: Call<Any>, response: Response<Any>) {
//                Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
//            }
//        })
//        aaaa(account?.idToken!!)
        updateUI(account)
    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                updateUI(null)
            }
    }

    fun test(view: View) {
        val service = RetrofitProvider.createService(this, AuthService::class.java)
        val helloWorld = service.helloWorld()
        helloWorld.enqueue(object : Callback<HelloWorld> {
            override fun onFailure(call: Call<HelloWorld>, t: Throwable) {
                Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<HelloWorld>, response: Response<HelloWorld>) {
                Toast.makeText(applicationContext, response.body()?.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }


//    fun aaaa(idToken: String) {
//        val client = OkHttpClient()
//        val requestBody: com.squareup.okhttp.RequestBody = FormEncodingBuilder()
//            .add("grant_type", "authorization_code")
//            .add(
//                "client_id",
//                "376972536110-e3ckka9ep06vhv33jq3o7vrnup177f4a.apps.googleusercontent.com"
//            )
//            .add("client_secret", "3oAVCfTLd0xD939w4E3OS3Q3")
//            .add("redirect_uri", "")
//            .add("code", "4/4-GMMhmHCXhWEzkobqIHGG_EnNYYsAkukHspeYUk9E8")
//            .add("id_token", idToken)
//            .build()
//        val request: Request = Request.Builder()
//            .url("https://www.googleapis.com/oauth2/v4/token")
//            .post(requestBody)
//            .build()
//        client.newCall(request).enqueue(object : com.squareup.okhttp.Callback {
//
//            override fun onFailure(request: Request?, e: IOException?) {
//                Log.e(TAG, e.toString())
//            }
//
//            override fun onResponse(response: com.squareup.okhttp.Response?) {
//                try {
//                    val jsonObject = JSONObject(response?.body()?.string()!!)
//                    val message = jsonObject.toString(5)
//                    Log.i(TAG, message)
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                }
//            }
//        })
//    }
}
