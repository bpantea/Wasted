package com.wasted.application.ui

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wasted.application.R
import com.wasted.application.backend.AuthService
import com.wasted.application.model.ConsumptionDto
import com.wasted.application.model.Drink
import com.wasted.application.utils.scanner.ScanActivity
import com.wasted.application.view_model.ConsumptionViewModel
import com.wasted.application.view_model.DrinkViewModel
import kotlinx.android.synthetic.main.activity_stats_layout.*

class ActivityStats : AppCompatActivity() {
    val ADD_CODE = 1
    val CREATE_CODE = 2
    val REQUEST_CODE = 1

    private lateinit var drinkViewModel: DrinkViewModel
    private lateinit var consumptionViewModel: ConsumptionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats_layout)
        bottomNavigationInit()

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CODE
            )
        }
        Log.d("Activity Stats", "onCreate: started...")

        drinkViewModel = ViewModelProvider(this).get(DrinkViewModel::class.java)
        consumptionViewModel = ViewModelProvider(this).get(ConsumptionViewModel::class.java)

        orderPlus.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            startActivityForResult(intent, ADD_CODE)
        }

    }

    fun createAndShowDialogForConfirmDrink(drink: Drink) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Adding a drink")
        builder.setMessage("Do you really want to add this drink?")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            consumptionViewModel.createConsumption(ConsumptionDto(AuthService.idUser!!, drink.id!!, drink.quantity!!))
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            // nothing
        }
        builder.show()
    }

    fun bottomNavigationInit() {

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
                    overridePendingTransition(0,0)
                    //mSelectedItem=1
                    true
                }
                else -> {
                    val intentThree = Intent(this, ScanActivity::class.java)
                    startActivityForResult(intentThree, CREATE_CODE)
                    overridePendingTransition(0,0)
                    true
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val code = data.getStringExtra("1")
            DrinkViewModel.scannerBarcode = code

            drinkViewModel.startGetDrink(code)

            drinkViewModel.currentDrink.observe(this, Observer {
                if (it != null) {
                    createAndShowDialogForConfirmDrink(it)
                } else {
                    Toast.makeText(this, "Drink not found!", Toast.LENGTH_LONG).show()
                }
            })
        }
        if (requestCode == CREATE_CODE && resultCode == Activity.RESULT_OK && data != null) {
            var code = data.getStringExtra("1")
            DrinkViewModel.scannerBarcode = code
            Toast.makeText(this, code, Toast.LENGTH_SHORT).show()
            var intent = Intent(this, ActivityCreateDrink::class.java)
            startActivity(intent)
        }
    }
}