package com.mny.wan.pkg.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mny.wan.pkg.R
import com.mny.mojito.base.BaseVBActivity
import com.mny.wan.pkg.databinding.ActivityMainViewPagerBinding
import com.mojito.common.ktx.enterFullScreen
import com.mny.wan.pkg.presentation.AppViewModel
import com.mny.wan.pkg.presentation.adapter.CommonFragmentViewPagerAdapter
import com.mny.wan.pkg.presentation.main.home.HomeFragment
import com.mny.wan.pkg.presentation.main.qa.QAFragment
import com.mny.wan.pkg.presentation.main.system.SystemFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainViewPagerActivity : BaseVBActivity<ActivityMainViewPagerBinding>() {

    private val mFragments = listOf<Fragment>(
        HomeFragment.newInstance(),
        QAFragment.newInstance(),
        SystemFragment.newInstance(),
    )

    private lateinit var mVpAdapter: CommonFragmentViewPagerAdapter

    @Inject
    lateinit var mAppViewModel: AppViewModel

    override fun initWindow(savedInstanceState: Bundle?) {
        super.initWindow(savedInstanceState)
        enterFullScreen()
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mVpAdapter =
            CommonFragmentViewPagerAdapter(supportFragmentManager, mFragments)
        mBinding.viewPager.adapter = mVpAdapter
        mBinding.viewPager.offscreenPageLimit = 4
        mBinding.viewPager.setDisableScroll()
        mBinding.navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    mBinding.viewPager.currentItem = 0
                }
                R.id.QAFragment -> {
                    mBinding.viewPager.currentItem = 1
                }
                R.id.systemFragment -> {
                    mBinding.viewPager.currentItem = 2
                }
                R.id.mineFragment -> {
                    mBinding.viewPager.currentItem = 3
                }
                else -> {
                }
            }
            true
        }
    }

}