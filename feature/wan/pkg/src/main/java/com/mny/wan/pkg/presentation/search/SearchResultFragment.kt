package com.mny.wan.pkg.presentation.search

import android.view.View
import androidx.fragment.app.activityViewModels
import com.mny.wan.pkg.base.BaseArticleFragment
import com.mny.wan.pkg.base.BaseArticlePageVM
import com.mny.wan.pkg.databinding.FragmentSearchResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : BaseArticleFragment<FragmentSearchResultBinding>() {

    private val mViewModel: SearchViewModel by activityViewModels()

    override fun articleViewModel(): BaseArticlePageVM = mViewModel

    override fun initView(view: View) {
        super.initView(view)
        initRefreshAndRv(mBinding.refresh, mBinding.rvArticles)
    }

}