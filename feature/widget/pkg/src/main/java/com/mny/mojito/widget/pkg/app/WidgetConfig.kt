package com.mny.mojito.widget.pkg.app

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.mny.mojito.base.delegate.AppLifecycle
import com.mny.mojito.di.module.GlobalModuleConfig
import com.mny.mojito.integration.ModuleConfig
import com.mny.mojito.utils.MojitoLog

class WidgetConfig : ModuleConfig {

    override fun applyOptions(context: Context, builder: GlobalModuleConfig.Builder) {

    }

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecycle>) {
        MojitoLog.d("injectAppLifecycle")
        lifecycles.add(WidgetAppLifecycleImpl())
    }

    override fun injectActivityLifecycle(
        context: Context,
        lifecycles: MutableList<Application.ActivityLifecycleCallbacks>
    ) {
        MojitoLog.d("injectActivityLifecycle")
    }

    override fun injectFragmentLifecycle(
        context: Context,
        lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>
    ) {
        MojitoLog.d("injectFragmentLifecycle")
    }

}