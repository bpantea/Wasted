package com.wasted.application.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wasted.application.R
import com.wasted.application.ui.drink.AddDrinkActivity
import com.wasted.application.utils.scanner.IntentScanner
import com.wasted.application.utils.scanner.ScanActivity
import kotlinx.android.synthetic.main.activity_stats_layout.*

class ActivityStats : AppCompatActivity(){

    val CODE = 1
    lateinit var intentScan: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats_layout)
        Log.d("Activity Stats", "onCreate: started...")


        orderPlus.setOnClickListener{
            IntentScanner.invoke(this)
            intentScan = IntentScanner.instance
            startActivityForResult(intentScan,CODE)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1  && resultCode == Activity.RESULT_OK && data!= null)
        {
            var code = data.getStringExtra("1")
            println(code)
            Toast.makeText(this,code,Toast.LENGTH_SHORT).show()
            if(!findDrinkByBarCode(code))
            {
                var intent = Intent(this,AddDrinkActivity::class.java)
                intent.putExtra("BarCode",code)
                startActivity(intent)
            }
        }
    }

    private fun findDrinkByBarCode(code: String?): Boolean {
        return false

    }
}