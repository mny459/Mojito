package com.mny.wan.user.pkg.presentation.about

import android.os.Bundle
import com.blankj.utilcode.util.AppUtils
import com.mojito.base.BaseToolbarActivity
import com.mny.wan.user.pkg.databinding.ActivityAboutBinding

class AboutActivity : BaseToolbarActivity<ActivityAboutBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initToolbar(mBinding.toolbar)
        mBinding.tvAppName.text = AppUtils.getAppName()
        mBinding.tvVerName.text = "${AppUtils.getAppVersionName()}-${AppUtils.getAppVersionCode()}"
    }

}