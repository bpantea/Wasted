package com.wasted.application.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wasted.application.R
import com.wasted.application.backend.AuthService
import com.wasted.application.utils.WastedApplication

class ActivityProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_layout)
        Log.d("Activity Profile", "onCreate: started...")


        val bottomNavigationView : BottomNavigationView = findViewById(R.id.bottomNavView)

        val menu : Menu = bottomNavigationView.menu
        val menuItem : MenuItem = menu.getItem(1)
        menuItem.setChecked(true)



        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_stats -> {
                    val intentOne = Intent(this, ActivityStats::class.java)
                    println("GOING TO 1")
                    ///////////// trebuie salvat Bundle inainte de callul la un nou Activity
                    startActivity(intentOne)
                    overridePendingTransition(0,0)
                    //mSelectedItem=0
                    true
                }
                R.id.ic_profile-> {
                    true
                }
                else -> {
                    val intentThree = Intent(this, ActivityCreateDrink::class.java)
                    startActivity(intentThree)
                    overridePendingTransition(0,0)
                    //mSelectedItem=2
                    true
                }

            }
            //            return true
        }
    }

    fun logOut(view: View) {
        (application as WastedApplication).mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                AuthService.updateCurrentUser(null)
            }
    }
}