package com.mny.wan.pkg.presentation.main.system.secondsystem

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mny.wan.pkg.base.BaseArticleFragment
import com.mny.wan.pkg.base.BaseArticlePageVM
import com.mny.wan.pkg.databinding.FragmentSystemChildrenArticleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SystemChildrenArticleFragment : BaseArticleFragment<FragmentSystemChildrenArticleBinding>() {
    private var mChildId = DEFAULT_CHILD_ID

    private val mViewModel: SystemChildrenArticleViewModel by viewModels()

    override fun articleViewModel(): BaseArticlePageVM = mViewModel

    override fun initArgs(bundle: Bundle?) {
        super.initArgs(bundle)
        mChildId = bundle?.getInt(CHILD_ID, DEFAULT_CHILD_ID) ?: DEFAULT_CHILD_ID
    }

    override fun onFirstInit() {
        mViewModel.initCId(mChildId)
        super.onFirstInit()
        mViewModel.loadData()
    }

    override fun initView(view: View) {
        super.initView(view)
        initRefreshAndRv(mBinding.refresh, mBinding.rvArticles)
    }

    companion object {
        const val CHILD_ID = "child_id"
        const val DEFAULT_CHILD_ID = -1
        fun newInstance(childId: Int): SystemChildrenArticleFragment {
            val fragment = SystemChildrenArticleFragment()
            val args = Bundle()
            args.putInt(CHILD_ID, childId)
            fragment.arguments = args
            return fragment
        }
    }
}