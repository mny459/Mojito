package com.mny.mojito.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseVBFragment<VB : ViewBinding> : BaseFragment(0) {

    private var _binding: VB? = null
    protected val mBinding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type = javaClass.genericSuperclass as ParameterizedType
        val aClass = type.actualTypeArguments[0] as Class<*>

        val binding = if (mRootView == null) {
            val method = aClass.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            )
            method.invoke(null, layoutInflater, container, false) as VB
        } else {
            // 把当前 root 从其父控件中移除
            (mRootView?.parent as? ViewGroup)?.apply {
                removeView(mRootView)
            }
            val method = aClass.getDeclaredMethod(
                "bind",
                View::class.java,
            )
            method.invoke(null, mRootView) as VB
        }
        binding.apply {
            _binding = this
            if (mRootView == null) {
                mRootView = root
                initView(root)
            }
        }
        return mRootView
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}