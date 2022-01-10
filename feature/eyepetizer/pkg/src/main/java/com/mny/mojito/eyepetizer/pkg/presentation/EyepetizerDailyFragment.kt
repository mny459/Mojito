package com.mny.mojito.eyepetizer.pkg.presentation

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.gyf.immersionbar.ImmersionBar
import com.mny.mojito.entension.observeWithViewLifecycle
import com.mny.mojito.eyepetizer.pkg.databinding.FragmentEyepetizerDailyBinding
import com.mny.mojito.eyepetizer.pkg.presentation.adapter.DailyAdapter
import com.mny.mojito.mvvm.BasePageVM
import com.mojito.base.BaseImmersionVBFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EyepetizerDailyFragment : BaseImmersionVBFragment<FragmentEyepetizerDailyBinding>() {

    private val mViewModel by viewModels<EyepetizerViewModel>()

    @Inject
    lateinit var mAdapter: DailyAdapter

    override fun initView(view: View) {
        super.initView(view)
        mBinding.refresh.setOnRefreshListener { mViewModel.refresh() }
        mBinding.refresh.setOnLoadMoreListener { mViewModel.loadMore() }
        mBinding.rvDaily.apply {
            layoutManager = LinearLayoutManager(mActivity)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    override fun onFirstInit() {
        super.onFirstInit()
        mViewModel.refresh()
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .titleBar(mBinding.statusBar)
            .statusBarDarkFont(true)
            .autoNavigationBarDarkModeEnable(true)
            .init()
    }

    override fun initViewObserver(viewLifecycleOwner: LifecycleOwner) {
        super.initViewObserver(viewLifecycleOwner)
        observeWithViewLifecycle(mViewModel.pageState) {
            LogUtils.d("${this.javaClass.simpleName} Page.V initObserver $it ${mViewModel.mItems.size}")
            when (it) {
                is BasePageVM.PageState.Complete -> {
                    val success = it.errorMsg.isEmpty()
                    if (it.refresh) mBinding.refresh.finishRefresh(0, success, it.noMoreData)
                    else mBinding.refresh.finishLoadMore(0, success, it.noMoreData)
                    if (success) {
                        mAdapter.submitList(mViewModel.mItems.toList())
                    }
                }
                else -> {
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = EyepetizerDailyFragment()
    }
}