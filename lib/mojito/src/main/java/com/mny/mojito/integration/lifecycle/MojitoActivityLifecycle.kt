package com.mny.mojito.integration.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.base.delegate.IActivity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 目前功能
 * 1. 打印生命周期日志
 */
@Singleton
class MojitoActivityLifecycle @Inject constructor() : Application.ActivityLifecycleCallbacks {

    @Inject
    lateinit var mFragmentLifecycle: MojitoFragmentLifecycle

    @Inject
    lateinit var mFragmentLifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        LogUtils.v("$activity - onActivityCreated savedInstanceState - $savedInstanceState")
        registerFragmentCallbacks(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        LogUtils.v("$activity - onActivityPaused")
    }

    override fun onActivityStarted(activity: Activity) {
        LogUtils.v("$activity - onActivityStarted")
    }

    override fun onActivityDestroyed(activity: Activity) {
        LogUtils.v("$activity - onActivityDestroyed")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        LogUtils.v("$activity - onActivitySaveInstanceState - $outState")
    }

    override fun onActivityStopped(activity: Activity) {
        LogUtils.v("$activity - onActivityStopped")
    }


    override fun onActivityResumed(activity: Activity) {
        LogUtils.v("$activity - onActivityResumed")
    }

    private fun registerFragmentCallbacks(activity: Activity) {
        val useFragment = activity !is IActivity || (activity as IActivity).useFragment()
        if (activity is FragmentActivity && useFragment) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                mFragmentLifecycle,
                true
            )
            for (fragmentLifecycle in mFragmentLifecycles) {
                activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                    fragmentLifecycle,
                    true
                )
            }
        }
    }
}