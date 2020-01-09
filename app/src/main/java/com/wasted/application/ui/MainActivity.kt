package com.wasted.application.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.wasted.application.R
import com.wasted.application.ui.auth.LoginFacebookActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun openLogin(view: View) {
        val intent = Intent(this, LoginFacebookActivity::class.java)
        startActivity(intent)
    }

}


//radu's comment