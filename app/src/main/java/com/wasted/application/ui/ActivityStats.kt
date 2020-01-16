package com.wasted.application.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wasted.application.R
import com.wasted.application.utils.scanner.ScanActivity
import kotlinx.android.synthetic.main.activity_stats_layout.*

class ActivityStats : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats_layout)
        Log.d("Activity Stats", "onCreate: started...")


        orderPlus.setOnClickListener{
            val intent:Intent  = Intent(this,ScanActivity::class.java)
            startActivity(intent)
        }

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavView)

        val menu: Menu = bottomNavigationView.menu
        val menuItem: MenuItem = menu.getItem(0)
        menuItem.setChecked(true)


        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_stats -> {
                    true
                }
                R.id.ic_profile -> {
                    val intentTwo = Intent(this, ActivityProfile::class.java)
                    startActivity(intentTwo)
                    //mSelectedItem=1
                    true
                }
                else -> {
                    val intentThree = Intent(this, ActivityCreateDrink::class.java)
                    startActivity(intentThree)
                    //mSelectedItem=2
                    true
                }

            }
            //            return true
        }
    }

}