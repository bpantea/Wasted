package com.wasted.application.ui.drink

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.wasted.application.R
import com.wasted.application.utils.scanner.IntentScanner

class AddDrinkActivity : AppCompatActivity() {
    val CODE = 1
    lateinit var intentScan: Intent
    lateinit var addDrink: Button
    companion object {
        var scanResult: EditText? = null
        var code :String =""

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_drink)
        code = intent.getStringExtra("BarCode") as String
        addDrink = findViewById(R.id.add_edit_drink)
        addDrink.setOnClickListener {
            if(code == "")
            {
                //casuta de dialog


            } else {
                if(isValid())
                {
                    //casuta de dialog => SIGUR VREI SA ADAUGI BAUTUR
                    addDrink()
                }
            }
        }

    }

    private fun addDrink() {
        //TO DB
        //TO CONSUMTION LIST
    }

    private fun isValid(): Boolean {
        return true
    }

    fun startScanButton(view: View) {
        IntentScanner.invoke(this)
        intentScan = IntentScanner.instance
        startActivityForResult(intentScan,CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1  && resultCode == Activity.RESULT_OK && data!= null)
        {
            var num1 = data.getStringExtra("1")
            println(num1)
            scanResult!!.setText(num1)
        }
    }
}