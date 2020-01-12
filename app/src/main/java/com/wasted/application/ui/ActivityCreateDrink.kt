package com.wasted.application.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wasted.application.R

class ActivityCreateDrink : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_drink_layout)
        Log.d("Activity CreateDrink", "onCreate: started...")

        val bottomNavigationView : BottomNavigationView = findViewById(R.id.bottomNavView)

        val menu : Menu = bottomNavigationView.menu
        val menuItem : MenuItem = menu.getItem(2)
        menuItem.setChecked(true)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_stats -> {
                    val intentOne = Intent(this, ActivityStats::class.java)
                    startActivity(intentOne)
                    true
                }
                R.id.ic_profile-> {
                    val intentTwo = Intent(this, ActivityProfile::class.java)
                    startActivity(intentTwo)
                    true
                }
                else -> {
                    true
                }
            }
            //            return true
        }
    }

}