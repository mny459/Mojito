package com.mny.wan.pkg.presentation.mine

import android.view.View
import androidx.fragment.app.activityViewModels
import com.mny.wan.pkg.base.BaseArticleFragment
import com.mny.wan.pkg.base.BaseArticlePageVM
import com.mny.wan.pkg.databinding.FragmentMineShareBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MineShareFragment : BaseArticleFragment<FragmentMineShareBinding>() {

    private val mViewModel by activityViewModels<MineShareViewModel>()

    override fun articleViewModel(): BaseArticlePageVM = mViewModel

    override fun initView(view: View) {
        super.initView(view)
        initRefreshAndRv(mBinding.refresh, mBinding.rvArticles)
    }

    override fun onFirstInit() {
        super.onFirstInit()
        mViewModel.loadData()
    }

}