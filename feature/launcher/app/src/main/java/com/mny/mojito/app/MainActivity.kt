package com.mny.mojito.app

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ColorUtils
import com.gyf.immersionbar.ImmersionBar
import com.mny.mojito.app.databinding.ActivityMainAppBinding
import com.mny.mojito.base.BaseVBActivity
import com.mny.mojito.eyepetizer.pkg.presentation.EyepetizerDailyFragment
import com.mojito.common.ktx.enterFullScreen
import com.mny.wan.pkg.presentation.AppViewModel
import com.mny.wan.pkg.presentation.adapter.CommonFragmentViewPagerAdapter
import com.mny.wan.pkg.presentation.main.home.HomeFragment
import com.mny.wan.pkg.presentation.main.qa.QAFragment
import com.mny.wan.pkg.presentation.main.system.SystemFragment
import com.mny.wan.user.pkg.presentation.mine.MineFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseVBActivity<ActivityMainAppBinding>() {

    private val mFragments = listOf<Fragment>(
        HomeFragment.newInstance(),
        QAFragment.newInstance(),
        SystemFragment.newInstance(),
        EyepetizerDailyFragment.newInstance(),
        MineFragment.newInstance()
    )

    private lateinit var mVpAdapter: CommonFragmentViewPagerAdapter

    @Inject
    lateinit var mAppViewModel: AppViewModel

    override fun initWindow(savedInstanceState: Bundle?) {
        super.initWindow(savedInstanceState)
        setTheme(R.style.AppTheme)
        window.setBackgroundDrawable(ColorDrawable(ColorUtils.getColor(R.color.colorSurface)))
        enterFullScreen()
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mVpAdapter =
            CommonFragmentViewPagerAdapter(supportFragmentManager, mFragments)
        mBinding.viewPager.adapter = mVpAdapter
        mBinding.viewPager.offscreenPageLimit = mFragments.size
        mBinding.viewPager.setDisableScroll()
        mBinding.navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    ImmersionBar.with(this)
                        .statusBarDarkFont(true)
                        .autoNavigationBarDarkModeEnable(true)
                        .init()
                    mBinding.viewPager.currentItem = 0
                }
                R.id.QAFragment -> {
                    mBinding.viewPager.currentItem = 1
                }
                R.id.systemFragment -> {
                    mBinding.viewPager.currentItem = 2
                }
                R.id.eyepetizerFragment -> {
                    mBinding.viewPager.currentItem = 3
                }
                R.id.mineFragment -> {
                    mBinding.viewPager.currentItem = 4
                }
                else -> {
                }
            }
            true
        }
    }

}