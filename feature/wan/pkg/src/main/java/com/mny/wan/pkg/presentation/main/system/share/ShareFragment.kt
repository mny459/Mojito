package com.mny.wan.pkg.presentation.main.system.share

import android.view.View
import androidx.fragment.app.activityViewModels
import com.mny.wan.pkg.base.BaseArticleFragment
import com.mny.wan.pkg.base.BaseArticlePageVM
import com.mny.wan.pkg.databinding.FragmentShareBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareFragment : BaseArticleFragment<FragmentShareBinding>() {

    private val mViewModel by activityViewModels<ShareViewModel>()
    override fun articleViewModel(): BaseArticlePageVM = mViewModel

    override fun initView(view: View) {
        super.initView(view)
        initRefreshAndRv(mBinding.refresh, mBinding.rvArticles)
    }

    companion object {
        fun newInstance(): ShareFragment = ShareFragment()
    }
}