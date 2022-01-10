package com.mny.mojito.base.delegate

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.mny.mojito.di.factory.GlobalConfigFactory
import com.mny.mojito.di.module.GlobalModuleConfig
import com.mny.mojito.integration.ManifestParser
import com.mny.mojito.integration.ModuleConfig
import com.mny.mojito.integration.lifecycle.MojitoActivityLifecycle
import com.mny.mojito.utils.MojitoLog
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class AppLifecycleDelegate(
    private val mApplication: Application?,
    private var mModuleConfigs: MutableList<ModuleConfig> = mutableListOf(),
    private val mAppLifecycleList: MutableList<AppLifecycle> = mutableListOf(),
    private val mActivityLifecycleList: MutableList<Application.ActivityLifecycleCallbacks> = mutableListOf(),
) : AppLifecycle {

    lateinit var mActivityLifecycle: Application.ActivityLifecycleCallbacks
    lateinit var mFragmentLifecycleList: MutableList<FragmentManager.FragmentLifecycleCallbacks>

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface MojitoEntryPoint {
        fun getMojitoActivityLifecycle(): MojitoActivityLifecycle
        fun getMojitoFragmentLifecycles(): MutableList<FragmentManager.FragmentLifecycleCallbacks>
    }

    init {
        mApplication?.apply {
            val parsedConfigs = ManifestParser(this).parse()
            mModuleConfigs.addAll(parsedConfigs)
            mModuleConfigs.forEach {
                it.injectAppLifecycle(this, mAppLifecycleList)
            }
        }
    }

    override fun attachBaseContext(base: Context) {
        mAppLifecycleList.forEach { it.attachBaseContext(base) }
    }

    override fun onCreate(application: Application) {
        val entryPoint =
            EntryPointAccessors.fromApplication(application, MojitoEntryPoint::class.java)
        mActivityLifecycle = entryPoint.getMojitoActivityLifecycle()
        mFragmentLifecycleList = entryPoint.getMojitoFragmentLifecycles()

        mModuleConfigs.forEach {
            it.injectActivityLifecycle(application, mActivityLifecycleList)
            it.injectFragmentLifecycle(application, mFragmentLifecycleList)
        }
        MojitoLog.d("onCreate ${mModuleConfigs.size}")
        // 初始化全局公共配置
        GlobalConfigFactory.saveGlobalModuleConfig(
            getGlobalModuleConfig(
                application,
                mModuleConfigs
            )
        )

        mActivityLifecycle.apply {
            mApplication?.registerActivityLifecycleCallbacks(this)
        }
        mActivityLifecycleList.forEach { mApplication?.registerActivityLifecycleCallbacks(it) }
        mAppLifecycleList.forEach { it.onCreate(application) }
    }


    private fun getGlobalModuleConfig(
        context: Context,
        configs: MutableList<ModuleConfig>
    ): GlobalModuleConfig {
        val builder = GlobalModuleConfig.Builder()
        configs.forEach { it.applyOptions(context, builder) }
        return builder.build()
    }


    override fun onTerminate(application: Application) {
        mActivityLifecycle.apply {
            application.unregisterActivityLifecycleCallbacks(this)
        }

        mActivityLifecycleList.forEach {
            application.unregisterActivityLifecycleCallbacks(it)
        }

        mAppLifecycleList.forEach { it.onTerminate(application) }

        mActivityLifecycleList.clear()
        mAppLifecycleList.clear()

    }
}