package com.mny.wan.pkg.presentation.main.system

import androidx.fragment.app.Fragment
import android.view.View
import com.blankj.utilcode.util.StringUtils
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ImmersionBar
import com.mny.wan.pkg.R
import com.mny.wan.pkg.databinding.FragmentSystemBinding
import com.mny.wan.pkg.presentation.adapter.CommonFragmentViewPagerAdapter
import com.mojito.base.BaseImmersionVBFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SystemFragment : BaseImmersionVBFragment<FragmentSystemBinding>() {

    private val mFragments = listOf<Fragment>(
        SystemTagFragment.newInstance(SystemTagFragment.TAG_SYSTEM),
        SystemTagFragment.newInstance(SystemTagFragment.TAG_NAV)
    )

    private val mTabs = listOf(
        StringUtils.getString(R.string.wan_nav_system),
        StringUtils.getString(R.string.wan_nav_guide)
    )

    private lateinit var mVpAdapter: CommonFragmentViewPagerAdapter

    override fun initView(view: View) {
        super.initView(view)
        mVpAdapter = CommonFragmentViewPagerAdapter(
            this.childFragmentManager,
            mFragments.toMutableList(),
            mTabs
        )
        mBinding.tabLayout.apply {
            removeAllTabs()
            tabMode = TabLayout.MODE_FIXED
            mBinding.viewPager.adapter = mVpAdapter
            setupWithViewPager(mBinding.viewPager)
        }
    }


    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(false)
            .autoNavigationBarDarkModeEnable(true)
            .init()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SystemFragment()
    }
}