package com.mny.wan.pkg.presentation.main.project

import android.os.Bundle
import com.gyf.immersionbar.ImmersionBar
import com.mojito.base.BaseToolbarActivity
import com.mny.wan.pkg.databinding.ActivityProjectBinding
import com.mny.wan.pkg.extension.initToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectActivity : BaseToolbarActivity<ActivityProjectBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initToolbar(mBinding.toolbar)
        ImmersionBar.with(this)
            .titleBar(mBinding.appbar)
            .statusBarDarkFont(false)
            .autoNavigationBarDarkModeEnable(true)
            .init()
    }

}