package com.mny.wan.pkg.app

import android.app.Application
import android.content.Context
import com.mny.mojito.base.delegate.AppLifecycle

class WanAppLifecycleImpl : AppLifecycle {

    override fun attachBaseContext(base: Context) {}

    override fun onCreate(application: Application) {}

    override fun onTerminate(application: Application) {}
}