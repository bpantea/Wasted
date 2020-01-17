package com.wasted.application.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wasted.application.R
import com.wasted.application.model.Drink
import com.wasted.application.view_model.DrinkViewModel

class ActivityCreateDrink : AppCompatActivity() {

    lateinit var drinkViewModel: DrinkViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_drink_layout)
        Log.d("Activity CreateDrink", "onCreate: started...")

        drinkViewModel = ViewModelProvider(this).get(DrinkViewModel::class.java)

        drinkViewModel.addDrink(Drink("123",
            123.0,
            "Another brand",
            "Model 1100",
            1334.0,
            233.0
        ))

        drinkViewModel.currentDrink.observe(this, Observer {
            Toast.makeText(this, it?.brand, Toast.LENGTH_LONG).show()
        })
        drinkViewModel.startGetDrink("123")

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