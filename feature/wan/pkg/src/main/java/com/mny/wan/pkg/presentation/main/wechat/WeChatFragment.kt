package com.mny.wan.pkg.presentation.main.wechat

import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.tabs.TabLayout
import com.mny.mojito.entension.observeWithViewLifecycle
import com.mny.wan.pkg.R
import com.mny.mojito.base.BaseVBFragment
import com.mny.mojito.entension.collectOnLifecycle
import com.mny.wan.pkg.databinding.FragmentWeChatBinding
import com.mny.wan.pkg.presentation.adapter.CommonFragmentViewPagerAdapter
import com.mny.wan.pkg.presentation.main.wechat.article.WeChatArticleFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeChatFragment : BaseVBFragment<FragmentWeChatBinding>() {

    private val mFragments = mutableListOf<Fragment>()
    private val mTabs = mutableListOf<String>()
    private lateinit var mVpAdapter: CommonFragmentViewPagerAdapter
    private val mViewModel: WeChatViewModel by activityViewModels()

    override fun initView(view: View) {
        super.initView(view)
        mBinding.toolbar.setTitle(R.string.we_chat_article)
        mBinding.toolbar.setNavigationOnClickListener {
            mActivity?.onBackPressed()
        }
        mBinding.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        mVpAdapter =
            CommonFragmentViewPagerAdapter(
                this@WeChatFragment.parentFragmentManager,
                mFragments,
                mTabs
            )
    }

    override fun initViewObserver(viewLifecycleOwner: LifecycleOwner) {
        super.initViewObserver(viewLifecycleOwner)
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
        fun newInstance() = WeChatFragment()
    }
}