package com.mny.wan.pkg.presentation.main.project

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.mny.mojito.entension.observeWithViewLifecycle
import com.mny.mojito.base.BaseVBFragment
import com.mny.wan.pkg.databinding.FragmentProjectBinding
import com.mny.wan.pkg.extension.collectOnLifecycle
import com.mny.wan.pkg.presentation.adapter.CommonFragmentViewPagerAdapter
import com.mny.wan.pkg.presentation.main.wechat.article.WeChatArticleFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProjectFragment : BaseVBFragment<FragmentProjectBinding>() {

    private val mFragments = mutableListOf<Fragment>()
    private val mTabs = mutableListOf<String>()
    private lateinit var mVpAdapter: CommonFragmentViewPagerAdapter
    private val mViewModel: ProjectViewModel by activityViewModels()

    override fun initView(view: View) {
        super.initView(view)
        mBinding.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        mVpAdapter =
            CommonFragmentViewPagerAdapter(this.parentFragmentManager, mFragments, mTabs)
    }

    override fun initOnceObserver(lifecycleOwner: LifecycleOwner) {
        super.initOnceObserver(lifecycleOwner)
        collectOnLifecycle(mViewModel.tabs, state = Lifecycle.State.CREATED) { tabs ->
            mBinding.tabLayout.apply {
                removeAllTabs()
                mFragments.clear()

                tabs.forEach { tab ->
                    mFragments.add(WeChatArticleFragment.newInstance(tab.id))
                    mTabs.add(tab.name)
                }

                mBinding.viewPager.adapter = mVpAdapter
                setupWithViewPager(mBinding.viewPager)
            }

        }
    }

    override fun onFirstInit() {
        super.onFirstInit()
        mViewModel.loadData()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProjectFragment()
    }

}