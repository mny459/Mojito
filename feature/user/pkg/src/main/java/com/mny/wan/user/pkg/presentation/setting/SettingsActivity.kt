package com.mny.wan.user.pkg.presentation.setting

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.entension.collectOnLifecycle
import com.mojito.base.BaseToolbarActivity
import com.mny.wan.user.pkg.databinding.ActivitySettingsBinding
import com.mny.wan.user.pkg.helper.UserRouterHelper
import com.mojito.common.helper.SettingHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : BaseToolbarActivity<ActivitySettingsBinding>() {
    private val mViewModel: SettingsViewModel by viewModels()

    @Inject
    lateinit var mSettingHelper: SettingHelper

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        // LogUtils.d("Setting.View initView $mViewModel")
        initToolbar(mBinding.toolbar)

        mBinding.rowThemeFollowSystem.setOnClickListener {
            mViewModel.switchThemeFollowSystem()
        }

        mBinding.rowThemeSwitch.setOnClickListener {
            mViewModel.switchThemeDark()
        }

        mBinding.rowLanguageSwitch.setOnClickListener {
            mViewModel.switchLanguage()
        }

        mBinding.rowX5Switch.setOnClickListener {
            mViewModel.switchUseX5()
        }

        mBinding.rowAbout.setOnClickListener {
            UserRouterHelper.goAbout()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun initObserver() {
        super.initObserver()
        collectOnLifecycle(mViewModel.useEnglishLanguage, Lifecycle.State.RESUMED) {
            LogUtils.d("initObserver useEnglishLanguage $it")
            mBinding.rowLanguageSwitch.setChecked(it, false)
        }
        collectOnLifecycle(mViewModel.useX5, Lifecycle.State.RESUMED) {
            LogUtils.d("initObserver useX5 $it")
            mBinding.rowX5Switch.setChecked(it, false)
        }
        collectOnLifecycle(mViewModel.theme, Lifecycle.State.RESUMED) {
            LogUtils.d("initObserver theme $it")
            mBinding.rowThemeFollowSystem.setChecked(it.followSystem, true)
            mBinding.rowThemeSwitch.isEnabled = !it.followSystem
            mBinding.rowThemeSwitch.setChecked(it.darkMode, false)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mViewModel.init()
    }

}