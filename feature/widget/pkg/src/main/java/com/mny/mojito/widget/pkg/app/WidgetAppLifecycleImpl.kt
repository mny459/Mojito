package com.mny.mojito.widget.pkg.app

import android.app.Application
import android.content.Context
import android.util.Log
import com.mny.mojito.base.delegate.AppLifecycle

class WidgetAppLifecycleImpl : AppLifecycle {
    companion object {
        private const val TAG = "WidgetAppLifecycleImpl"
    }

    override fun attachBaseContext(base: Context) {
        Log.d(TAG, "attachBaseContext: ")
    }

    override fun onCreate(application: Application) {
        Log.d(TAG, "onCreate: ")
    }

    override fun onTerminate(application: Application) {
        Log.d(TAG, "onTerminate: ")
    }
}