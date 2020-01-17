package com.wasted.application.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wasted.application.R
import com.wasted.application.backend.AuthService
import com.wasted.application.model.ExtraFieldsUser
import com.wasted.application.model.Gender
import com.wasted.application.model.User
import com.wasted.application.utils.WastedApplication
import com.wasted.application.view_model.UserViewModel
import kotlinx.android.synthetic.main.activity_profile_layout.*
import java.util.*

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
           var genSpinner:Spinner=findViewById(R.id.genderSpinner)
            if(user.gender!=null){
                if(user.gender!!.equals(Gender.MALE))
                genSpinner.setSelection(0)
                if(user.gender!!.equals(Gender.FEMALE))
                    genSpinner.setSelection(1)
                if(user.gender!!.equals(Gender.OTHER))
                    genSpinner.setSelection(2)
            }

            var wghtUser:EditText =findViewById(R.id.weightText)
            if(user.weight!=null)
                wghtUser.setText(user.weight.toString())
            else
                wghtUser.setText("0")

//            var date:DatePicker=findViewById(R.id.datePickerInfo)
//            var data: Date? = user.birthday
//            if(data!=null)
//                date.updateDate(date.year,date.month,date.dayOfMonth)

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


        var user = ExtraFieldsUser(null, null, null)
        var genSpinner:Spinner=findViewById(R.id.genderSpinner)
        if(genSpinner.selectedItem.equals(genSpinner.getItemAtPosition(0)))
            user.gender=Gender.MALE
        if(genSpinner.selectedItem.equals(genSpinner.getItemAtPosition(1)))
            user.gender=Gender.FEMALE
        if(genSpinner.selectedItem.equals(genSpinner.getItemAtPosition(2)))
            user.gender=Gender.OTHER
        var wghtUser:EditText =findViewById(R.id.weightText)
        user.weight=wghtUser.text.toString().toDouble()
        var date:DatePicker=findViewById(R.id.datePickerInfo)
        var data: Date?
      var calendar:Calendar= Calendar.getInstance()
        calendar.set(date.year,date.month,date.dayOfMonth)
       data=calendar.time
        user.birthday=data
      Toast.makeText(this,data.toString(),Toast.LENGTH_LONG).show()
        //print(user.toString())
        userViewModel.updateUser(user)
    }
}