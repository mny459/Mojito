package com.mny.wan.pkg.presentation

import android.os.Bundle
import com.mny.mojito.base.BaseActivity
import com.mny.wan.pkg.extension.goMain

class LauncherActivity : BaseActivity() {
    override fun layoutId(savedInstanceState: Bundle?): Int = 0
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        goMain()
        this@LauncherActivity.finish()
    }
}