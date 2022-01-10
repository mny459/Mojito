package com.mny.mojito.widget.pkg

import android.os.Bundle
import com.gyf.immersionbar.ImmersionBar
import com.mny.mojito.base.BaseVBActivity
import com.mny.mojito.widget.pkg.databinding.ActivityMainBinding
import com.mojito.common.helper.ThemeHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseVBActivity<ActivityMainBinding>() {

    override fun initWindow(savedInstanceState: Bundle?) {
        super.initWindow(savedInstanceState)
        setTheme(R.style.AppTheme)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        ImmersionBar.with(this)
            .statusBarColorInt(ThemeHelper.getColorByAttr(R.attr.background))
            .fitsSystemWindows(true)
            .statusBarDarkFont(true)
            .init()
    }

}