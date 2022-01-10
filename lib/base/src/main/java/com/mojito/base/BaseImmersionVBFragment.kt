package com.mojito.base

import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.components.ImmersionOwner
import com.gyf.immersionbar.components.ImmersionProxy
import com.mny.mojito.base.BaseVBFragment

/**
 * BaseImmersionVBFragment
 * Desc:
 */
abstract class BaseImmersionVBFragment<VB : ViewBinding> : BaseVBFragment<VB>(), ImmersionOwner {
    /**
     * ImmersionBar代理类
     */
    private val mImmersionProxy = ImmersionProxy(this)

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mImmersionProxy.isUserVisibleHint = isVisibleToUser

    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mImmersionProxy.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mImmersionProxy.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mImmersionProxy.onResume()
    }

    override fun onPause() {
        super.onPause()
        mImmersionProxy.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mImmersionProxy.onDestroy()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mImmersionProxy.onHiddenChanged(hidden)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mImmersionProxy.onConfigurationChanged(newConfig)
    }


    /**
     * 懒加载，在view初始化完成之前执行
     * On lazy after view.
     */
    override fun onLazyBeforeView() {}

    /**
     * 懒加载，在view初始化完成之后执行
     * On lazy before view.
     */
    override fun onLazyAfterView() {}

    /**
     * Fragment用户可见时候调用
     * On visible.
     */
    override fun onVisible() {}

    /**
     * Fragment用户不可见时候调用
     * On invisible.
     */
    override fun onInvisible() {}

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    override fun immersionBarEnabled(): Boolean {
        return true
    }
}