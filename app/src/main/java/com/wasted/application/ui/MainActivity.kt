package com.wasted.application.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.viewpager.widget.ViewPager

import com.wasted.application.R
import com.wasted.application.ui.auth.LoginFacebookActivity

class MainActivity : AppCompatActivity() {



    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_stats_layout)
        val intent = Intent(this, ActivityStats::class.java)
        startActivity(intent)
        Log.d(TAG, "onCreate: Started...")

       /*val btnSecondActivity = findViewById<Button>(R.id.buttonSecondActivity)
        btnSecondActivity.setOnClickListener{
            val intent = Intent(this, ActivityStats::class.java)
            startActivity(intent)
        }*/

    }

    fun openLogin(view: View) {
        val intent = Intent(this, LoginFacebookActivity::class.java)
        startActivity(intent)
    }







    companion object {

        private val TAG = "ActivityStats"
    }
}
