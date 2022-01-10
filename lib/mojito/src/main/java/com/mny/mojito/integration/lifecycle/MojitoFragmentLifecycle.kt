package com.mny.mojito.integration.lifecycle

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.blankj.utilcode.util.LogUtils
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MojitoFragmentLifecycle
 * Desc:
 */
@Singleton
class MojitoFragmentLifecycle @Inject constructor() : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentPreAttached(fm, f, context)
        LogUtils.v("onFragmentPreAttached() called with: f = $f")
    }

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentAttached(fm, f, context)
        LogUtils.v("onFragmentAttached() called with: f = $f")
    }

    override fun onFragmentPreCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentPreCreated(fm, f, savedInstanceState)
        LogUtils.v(
            "onFragmentPreCreated() called with: f = $f"
        )
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentCreated(fm, f, savedInstanceState)
        LogUtils.v(
            "onFragmentCreated() called with: f = $f"
        )
    }

    override fun onFragmentActivityCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState)
        LogUtils.v(
            "onFragmentActivityCreated() called with: f = $f"
        )
    }

    override fun onFragmentViewCreated(
        fm: FragmentManager,
        f: Fragment,
        v: View,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
        LogUtils.v(
            "onFragmentViewCreated() called with: f = $f"
        )
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        super.onFragmentStarted(fm, f)
        LogUtils.v("onFragmentStarted() called with: f = $f")
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        super.onFragmentResumed(fm, f)
        LogUtils.v("onFragmentResumed() called with: f = $f")
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        super.onFragmentPaused(fm, f)
        LogUtils.v("onFragmentPaused() called with: f = $f")
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        super.onFragmentStopped(fm, f)
        LogUtils.v("onFragmentStopped() called with: f = $f")
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        super.onFragmentSaveInstanceState(fm, f, outState)
        LogUtils.v(
            "onFragmentSaveInstanceState() called with: f = $f"
        )
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentViewDestroyed(fm, f)
        LogUtils.v("onFragmentViewDestroyed() called with: f = $f")
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentDestroyed(fm, f)
        LogUtils.v("onFragmentDestroyed() called with: f = $f")
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        super.onFragmentDetached(fm, f)
        LogUtils.v("onFragmentDetached() called with: f = $f")
    }
}