package com.mny.mojito.integration

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.mny.mojito.base.delegate.AppLifecycle
import com.mny.mojito.di.module.GlobalModuleConfig

interface ModuleConfigAdapter :ModuleConfig{
    override fun applyOptions(context: Context, builder: GlobalModuleConfig.Builder) {

    }

    override fun injectActivityLifecycle(
        context: Context,
        lifecycles: MutableList<Application.ActivityLifecycleCallbacks>
    ) {

    }

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecycle>) {

    }

    override fun injectFragmentLifecycle(
        context: Context,
        lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>
    ) {

    }
}