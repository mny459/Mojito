package com.mny.wan.pkg.presentation.main.system.share

import android.os.Bundle
import com.mojito.base.BaseToolbarActivity
import com.mny.wan.pkg.databinding.ActivityShareBinding
import com.mny.wan.pkg.extension.initToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareActivity : BaseToolbarActivity<ActivityShareBinding>() {
    
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initToolbar(mBinding.toolbar)
    }

}