package com.mny.wan.pkg.presentation.collect

import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.appbar.MaterialToolbar
import com.mny.wan.pkg.R
import com.mny.wan.pkg.base.BaseArticleFragment
import com.mny.wan.pkg.base.BaseArticlePageVM
import com.mny.wan.pkg.databinding.FragmentCollectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectFragment : BaseArticleFragment<FragmentCollectBinding>() {

    private val mViewModel: CollectArticleViewModel by viewModels()

    override fun articleViewModel(): BaseArticlePageVM = mViewModel

    override fun initView(view: View) {
        super.initView(view)
        mBinding.toolbar.setNavigationOnClickListener {
            mActivity?.onBackPressed()
        }
        initRefreshAndRv(mBinding.refresh, mBinding.rvArticles)
    }
}