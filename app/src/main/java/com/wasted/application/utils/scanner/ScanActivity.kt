package com.wasted.application.utils.scanner

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView


/**
 * Created by Parsania Hardik on 19-Mar-18.
 */
class ScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    val CODE:String = "1"
    val REQUEST_CODE = 100
    private var mScannerView: ZXingScannerView? = null

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CODE)
            mScannerView = ZXingScannerView(this)   // Programmatically initialize the scanner view
            setContentView(mScannerView) // Set the scanner view as the content view

        }
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera()          // Start camera on resume
    }

    public override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()           // Stop camera on pause
    }

    override fun handleResult(barcodeResult: Result?) {
        val output = Intent()
        output.putExtra(CODE, barcodeResult.toString())
        setResult(Activity.RESULT_OK, output)
        finish()
    }
}