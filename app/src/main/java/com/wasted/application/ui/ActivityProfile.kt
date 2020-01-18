package com.wasted.application.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
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
import com.wasted.application.utils.scanner.ScanActivity
import com.wasted.application.view_model.DrinkViewModel
import com.wasted.application.view_model.UserViewModel
import java.util.*


class ActivityProfile : AppCompatActivity() {
    private val CREATE_CODE = 2

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
            val genSpinner: Spinner = findViewById(R.id.genderSpinner)
            if (user.gender != null) {
                if (user.gender!!.equals(Gender.MALE))
                    genSpinner.setSelection(0)

                if (user.gender!!.equals(Gender.FEMALE))
                    genSpinner.setSelection(1)

                if (user.gender!!.equals(Gender.OTHER))
                    genSpinner.setSelection(2)
            }

            val weightText: EditText = findViewById(R.id.weightText)
            weightText.setText(if (user.weight != null) user.weight.toString() else "0")

            val date: DatePicker = findViewById(R.id.datePickerInfo)
            val calendar: Calendar = Calendar.getInstance()

            if (user.birthday != null) {
                calendar.timeInMillis = user.birthday!!.time
            }
            date.updateDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
        if (user != null) {
            if (!user.profilePicture.equals("null")) {


                val profilePicture: ImageView = findViewById(R.id.userImage)
                userViewModel.bmpObservable.observe(this, Observer {
                    if (it != null) {
                        profilePicture.setImageBitmap(it)
                    }
                })
                UserViewModel.src = user.profilePicture!!
                userViewModel.fetchProfilePicture()
            }
        }
        var displayNameLabel: TextView = findViewById(R.id.displayNameText)
        displayNameLabel.setText(user?.displayName.toString())

        var emailLabel: TextView = findViewById(R.id.emailText)
        emailLabel.setText(user?.email.toString())
    }

    private fun bottomNavigationInitialization() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavView)

        val menu: Menu = bottomNavigationView.menu
        val menuItem: MenuItem = menu.getItem(1)
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
                    true
                }
                else -> {
                    val intentThree = Intent(this, ScanActivity::class.java)
                    startActivityForResult(intentThree, CREATE_CODE)
                    overridePendingTransition(0, 0)
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
        val genSpinner: Spinner = findViewById(R.id.genderSpinner)
        val weightText: EditText = findViewById(R.id.weightText)
        val date: DatePicker = findViewById(R.id.datePickerInfo)
        val calendar = Calendar.getInstance()
        calendar.set(date.year, date.month, date.dayOfMonth)

        val user = ExtraFieldsUser(null, null, null)

        user.gender = Gender.valueOf(genSpinner.selectedItem.toString().toUpperCase())
        user.weight = weightText.text.toString().toDouble()
        user.birthday = calendar.timeInMillis

        userViewModel.updateUser(user)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val code = data.getStringExtra("1")
            DrinkViewModel.scannerBarcode = code
            val intent = Intent(this, ActivityCreateDrink::class.java)
            startActivity(intent)
        }
    }
}