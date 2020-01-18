package com.wasted.application.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wasted.application.R
import com.wasted.application.model.Drink
import com.wasted.application.view_model.DrinkViewModel

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
        val quantity: EditText = findViewById(R.id.quantity)
        val brand: EditText = findViewById(R.id.brand)
        val model: EditText = findViewById(R.id.model)
        val bloodAlcohol: EditText = findViewById(R.id.bloodAlcohol)
        val kcal: EditText = findViewById(R.id.kcal)
        quantity.setText(drink.quantity.toString())
        brand.setText(drink.brand)
        model.setText(drink.model)
        bloodAlcohol.setText(drink.alcoholQuantity.toString())
        kcal.setText(drink.kcal.toString())
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
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.ic_profile -> {
                    val intentTwo = Intent(this, ActivityProfile::class.java)
                    startActivity(intentTwo)
                    overridePendingTransition(0, 0)
                    true
                }
                else -> {
                    true
                }
            }
        }
    }

    fun addDrink(view: View?) {
        drinkViewModel.addDrink(getDrinkFromEditView())
    }

    private fun getDrinkFromEditView(): Drink {
        val quantity: Double = findViewById<EditText>(R.id.quantity).text.toString().toDouble()
        val brand: String = findViewById<EditText>(R.id.brand).text.toString()
        val model: String = findViewById<EditText>(R.id.model).text.toString()
        val bloodAlcohol: Double =
            findViewById<EditText>(R.id.bloodAlcohol).text.toString().toDouble()
        val kcal: Double = findViewById<EditText>(R.id.kcal).text.toString().toDouble()
        return Drink(
            DrinkViewModel.scannerBarcode!!.toString(),
            quantity,
            brand,
            model,
            bloodAlcohol,
            kcal
        )
    }
}