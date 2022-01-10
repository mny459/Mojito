package com.mojito.common.app

import android.app.Application
import android.content.Context
import com.mny.mojito.base.delegate.AppLifecycle
import com.mojito.common.app.tasks.AppStart
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class CommonAppLifecycleImpl : AppLifecycle {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface AppStartPoint {
        fun getAppStart(): AppStart
    }

    override fun attachBaseContext(base: Context) {}

    override fun onCreate(application: Application) {
        val entryPoint =
            EntryPointAccessors.fromApplication(application, AppStartPoint::class.java)
        val appStart = entryPoint.getAppStart()
        appStart.start()
    }

    override fun onTerminate(application: Application) {}
}