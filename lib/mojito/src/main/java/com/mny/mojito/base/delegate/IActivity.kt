package com.mny.mojito.base.delegate

import android.os.Bundle

interface IActivity {
    /**
     * called before super.onCreate(savedInstanceState)
     */
    fun initWindow(savedInstanceState: Bundle?)

    /**
     * called before setContentView
     */
    fun initArgs(bundle: Bundle?, savedInstanceState: Bundle?): Boolean

    /**
     * called before setContentView
     * if return 0, setContentView wont be called
     */
    fun layoutId(savedInstanceState: Bundle?): Int

    /**
     * called after setContentView
     */
    fun initView(savedInstanceState: Bundle?)

    /**
     * called after initView
     */
    fun initObserver()

    /**
     * called after initObserver
     */
    fun initData(savedInstanceState: Bundle?)
    fun useEventBus(): Boolean
    fun useFragment() = true
}