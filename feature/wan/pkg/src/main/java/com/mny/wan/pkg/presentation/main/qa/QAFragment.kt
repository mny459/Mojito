package com.mny.wan.pkg.presentation.main.qa

import android.view.View
import androidx.fragment.app.activityViewModels
import com.gyf.immersionbar.ImmersionBar
import com.mny.wan.pkg.base.BaseArticleFragment
import com.mny.wan.pkg.base.BaseArticlePageVM
import com.mny.wan.pkg.databinding.FragmentQABinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QAFragment : BaseArticleFragment<FragmentQABinding>() {

    private val mViewModel: QAViewModel by activityViewModels()

    override fun articleViewModel(): BaseArticlePageVM = mViewModel

    override fun initView(view: View) {
        super.initView(view)
        initRefreshAndRv(mBinding.refresh, mBinding.rvArticles)
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(false)
            .autoNavigationBarDarkModeEnable(true)
            .init()
    }

    companion object {
        @JvmStatic
        fun newInstance() = QAFragment()
    }

}