package com.wasted.application.utils.scanner

import android.app.Activity
import android.content.Intent

object IntentScanner {
    private var newActivity: Activity?=null
    operator fun invoke(s: Activity) {
        newActivity = s
    }
    private var ourIntent: Intent?=null
    val instance: Intent
        get(){
            var current_activity: Activity?=null
            if(ourIntent == null || current_activity != newActivity)
            {
                current_activity = newActivity
                ourIntent = Intent(newActivity,ScanActivity::class.java)
            }
            return ourIntent!!

        }
}