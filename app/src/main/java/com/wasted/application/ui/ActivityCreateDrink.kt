package com.wasted.application.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wasted.application.R
import com.wasted.application.utils.scanner.IntentScanner

class ActivityCreateDrink : AppCompatActivity() {
    val CODE = 1

    lateinit var intentScan: Intent
    lateinit var addDrink: Button
    companion object {
        var scanResult: String?= null
        var code :String =""

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_drink_layout)
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

        if(!intent.hasExtra("BarCode"))
        {
            startScanButton()

        }
        else {
            code = intent.getStringExtra("BarCode") as String
        }

        addDrink = findViewById(R.id.add_edit_drink)
        addDrink.setOnClickListener {

            if (isValid()) {

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Adding a drink")
                builder.setMessage("Do you really want to add this drink?")

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    //action if yes
                }

                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    //action if cancel
                }

                builder.show()
            }
        }

        Log.d("Activity CreateDrink", "onCreate: started...")


    }
    private fun addDrinkToDB() {
    }

    fun isValid(): Boolean {
        return true
    }

    fun startScanButton() {
        IntentScanner.invoke(this)
        intentScan = IntentScanner.instance
        startActivityForResult(intentScan,CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1  && resultCode == Activity.RESULT_OK && data!= null)
        {
            var num1 = data.getStringExtra("1")
            // println(num1)
            scanResult =num1
        }
    }

}