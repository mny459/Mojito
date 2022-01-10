package com.mny.wan.pkg.presentation.main.project.article

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mny.wan.pkg.base.BaseArticleFragment
import com.mny.wan.pkg.base.BaseArticlePageVM
import com.mny.wan.pkg.databinding.FragmentProjectArticleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectArticleFragment : BaseArticleFragment<FragmentProjectArticleBinding>() {

    private var mCid: Int = 0
    private val mViewModel: ProjectArticleViewModel by viewModels()

    override fun articleViewModel(): BaseArticlePageVM = mViewModel

    override fun initArgs(bundle: Bundle?) {
        super.initArgs(bundle)
        bundle?.let {
            mCid = it.getInt(ARG_CID, 0)
        }
    }

    override fun initView(view: View) {
        super.initView(view)
        initRefreshAndRv(mBinding.refresh, mBinding.rvArticles)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mViewModel.initCId(mCid)
        super.initData(savedInstanceState)
    }

    companion object {
        private const val ARG_CID = "c_id"
        @JvmStatic
        fun newInstance(cid: Int) =
            ProjectArticleFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CID, cid)
                }
            }
    }
}