package com.wasted.application.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wasted.application.R
import com.wasted.application.model.Drink
import com.wasted.application.view_model.DrinkViewModel
import java.lang.Exception

class ActivityCreateDrink : AppCompatActivity() {

    private lateinit var drinkViewModel: DrinkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_drink_layout)

        Log.d("Activity CreateDrink", "onCreate: started...")

        bottomNavigationInit()
        drinkViewModel = ViewModelProvider(this).get(DrinkViewModel::class.java)

        drinkViewModel.startGetDrink(DrinkViewModel.scannerBarcode!!)
        drinkViewModel.currentDrink.observe(this, Observer {
            if (it != null) {
                updateUIFromDrink(it)
            }
        })
    }

    private fun updateUIFromDrink(drink: Drink) {
        // todo update UI
        Toast.makeText(this, drink.brand, Toast.LENGTH_LONG).show()
    }

    private fun bottomNavigationInit() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavView)

        val menu: Menu = bottomNavigationView.menu
        val menuItem: MenuItem = menu.getItem(2)
        menuItem.isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_stats -> {
                    val intentOne = Intent(this, ActivityStats::class.java)
                    startActivity(intentOne)
                    true
                }
                R.id.ic_profile -> {
                    val intentTwo = Intent(this, ActivityProfile::class.java)
                    startActivity(intentTwo)
                    true
                }
                else -> {
                    true
                }
            }
        }
    }

    fun addDrink(view: View?) {
        // todo: put real values here
        val drink = Drink(DrinkViewModel.scannerBarcode!!, 100.0, "brand", "model", 100.0, 100.0)
        try {
            drinkViewModel.addDrink(drink)
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }
}