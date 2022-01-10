package com.mny.mojito.base

import android.app.Application
import android.content.Context
import com.mny.mojito.base.delegate.AppLifecycleDelegate
import com.mny.mojito.base.delegate.AppLifecycle

abstract class BaseApplication : Application() {

    private lateinit var mAppDelegate: AppLifecycle

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        mAppDelegate = AppLifecycleDelegate(this)
        mAppDelegate.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        mAppDelegate.onCreate(this)
    }

    override fun onTerminate() {
        mAppDelegate.onTerminate(this)
        super.onTerminate()
    }

}