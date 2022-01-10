package com.mny.mojito.base.delegate

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner

interface IFragment {
    /**
     * called after Fragment#onAttach called
     */
    fun initArgs(bundle: Bundle?)
    fun initView(view: View)

    /**
     * call after Fragment#onViewCreated called every time
     */
    fun initViewObserver(viewLifecycleOwner: LifecycleOwner)

    /**
     * call after Fragment#onViewCreated called once
     */
    fun initOnceObserver(lifecycleOwner: LifecycleOwner)
    fun initData(savedInstanceState: Bundle?)
    fun useEventBus(): Boolean
}