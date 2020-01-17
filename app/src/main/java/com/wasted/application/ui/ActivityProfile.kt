package com.wasted.application.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wasted.application.R
import com.wasted.application.backend.AuthService
import com.wasted.application.model.ExtraFieldsUser
import com.wasted.application.model.User
import com.wasted.application.utils.WastedApplication
import com.wasted.application.view_model.UserViewModel

class ActivityProfile : AppCompatActivity() {

    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_layout)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        bottomNavigationInitialization()
        userViewModel.fetchFullUser()
        userViewModel.fullUser.observe(this, Observer {
            updateUI(it)
        })
    }

    private fun updateUI(user: User?) {
        if (user != null) {
            // todo add here info from user
        }
    }

    private fun bottomNavigationInitialization() {
        val bottomNavigationView : BottomNavigationView = findViewById(R.id.bottomNavView)

        val menu : Menu = bottomNavigationView.menu
        val menuItem : MenuItem = menu.getItem(1)
        menuItem.isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_stats -> {
                    val intentOne = Intent(this, ActivityStats::class.java)
                    println("GOING TO 1")
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
        }
    }

    fun logOut(view: View) {
        (application as WastedApplication).mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                AuthService.updateAuthenticationUser(null)
            }
    }

    fun updateUser(view: View) {
        // TODO update this fields from Views
        val user = ExtraFieldsUser(null, null, null)
        userViewModel.updateUser(user)
    }
}