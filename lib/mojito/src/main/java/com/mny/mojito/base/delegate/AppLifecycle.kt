package com.mny.mojito.base.delegate

import android.app.Application
import android.content.Context

interface AppLifecycle {
    fun attachBaseContext(base: Context)

    fun onCreate(application: Application)

    fun onTerminate(application: Application)
}
