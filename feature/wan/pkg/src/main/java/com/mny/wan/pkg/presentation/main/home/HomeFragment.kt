package com.mny.wan.pkg.presentation.main.home

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ConcatAdapter
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.wan.pkg.base.BaseArticleFragment
import com.mny.wan.pkg.base.BaseArticlePageVM
import com.mny.wan.pkg.databinding.FragmentHomeBinding
import com.mny.wan.pkg.extension.collectOnLifecycle
import com.mny.wan.pkg.extension.goSearch
import com.mny.wan.pkg.extension.goWidget
import com.mny.wan.pkg.presentation.adapter.BannerAdapter
import com.mny.wan.pkg.presentation.adapter.TopArticleAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseArticleFragment<FragmentHomeBinding>() {

    private val mViewModel: HomeViewModel by activityViewModels()

    override fun articleViewModel(): BaseArticlePageVM = mViewModel

    private val mAllAdapter: ConcatAdapter by lazy { ConcatAdapter() }

    @Inject
    lateinit var mBannerAdapter: BannerAdapter

    @Inject
    lateinit var mTopArticleAdapter: TopArticleAdapter

    override fun initView(view: View) {
        super.initView(view)
        mTopArticleAdapter.viewLifecycleOwner = viewLifecycleOwner
        initRefreshAndRv(mBinding.refresh, mBinding.rvArticles)
        mBinding.floatSearch.setOnDebouncedClickListener { goSearch() }
        mBinding.floatWidget.setOnDebouncedClickListener { goWidget() }
        mAllAdapter.addAdapter(0, mBannerAdapter)
        mAllAdapter.addAdapter(1, mTopArticleAdapter)
        mAllAdapter.addAdapter(2, mAdapter)
        mBinding.rvArticles.adapter = mAllAdapter
    }

    override fun initViewObserver(viewLifecycleOwner: LifecycleOwner) {
        super.initViewObserver(viewLifecycleOwner)
        collectOnLifecycle(mViewModel.bannerList) { list ->
            mBannerAdapter.replaceBanners(list)
        }
        collectOnLifecycle(mViewModel.topArticles) { list ->
            mTopArticleAdapter.submitList(list)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

}