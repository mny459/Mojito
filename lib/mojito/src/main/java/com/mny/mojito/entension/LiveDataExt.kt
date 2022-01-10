package com.mny.mojito.entension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

// Activity 和 Fragment 会用这个
@Deprecated("use flow please", ReplaceWith("liveData.observe(this, observer)"))
fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: Observer<T>) {
    liveData.observe(this, observer)
}

// Fragment 一般的 UI 推荐使用这个
@Deprecated("use flow please", ReplaceWith(""))
fun <T> Fragment.observeWithViewLifecycle(liveData: LiveData<T>, observer: Observer<T>) {
    liveData.observe(viewLifecycleOwner, observer)
}

@Deprecated("use flow please", ReplaceWith("this as LiveData<T>", "androidx.lifecycle.LiveData"))
fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>