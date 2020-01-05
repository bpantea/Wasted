package com.wasted.application.utils

import android.app.Application
import com.facebook.appevents.AppEventsLogger

class WastedApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppEventsLogger.activateApp(this)
    }
}