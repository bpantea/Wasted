package com.wasted.application.ui.drink

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.wasted.application.R
import com.wasted.application.utils.scanner.IntentScanner

class AddDrinkActivity : AppCompatActivity() {
    val CODE = 1
    lateinit var intentScan: Intent
    companion object {

        var scanResult: EditText? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_drink)
        scanResult = findViewById(R.id.scanResult) as EditText


    }
    fun startScanButton(view: View) {
        IntentScanner.invoke(this)
        intentScan = IntentScanner.instance
        startActivityForResult(intentScan,CODE)
    }
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1  && resultCode == Activity.RESULT_OK && data!= null)
        {
            var num1 = data.getStringExtra("1")
            println(num1)
            scanResult!!.setText(num1)
        }
    }
}
