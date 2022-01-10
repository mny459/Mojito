package com.mny.wan.user.pkg.presentation.coin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.LogUtils
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.mny.mojito.entension.observeWithViewLifecycle
import com.mny.mojito.base.BaseVBFragment
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.mojito.mvvm.BasePageVM
import com.mny.wan.user.pkg.databinding.FragmentCoinDetailBinding
import com.mny.wan.user.pkg.helper.UserRouterHelper
import com.mny.wan.user.pkg.presentation.adapter.coin.CoinDetailAdapter
import com.mojito.common.R
import com.mojito.common.constants.DIVIDER_INSET
import com.mojito.common.data.local.UserHelper
import com.mojito.common.helper.ThemeHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CoinDetailFragment : BaseVBFragment<FragmentCoinDetailBinding>() {
    private val mViewModel: CoinDetailViewModel by viewModels()

    @Inject
    lateinit var mAdapter: CoinDetailAdapter

    override fun initView(view: View) {
        super.initView(view)
        mBinding.ivBack.setOnDebouncedClickListener { mActivity?.onBackPressed() }
        mBinding.ivRankList.setOnDebouncedClickListener { UserRouterHelper.goCoinDetail(false) }
        mBinding.tvTotalCoin.text = UserHelper.userCoin?.coinCount?.toString()
        with(mBinding.refresh) {
            setOnRefreshListener { mViewModel.refresh() }
            setOnLoadMoreListener { mViewModel.loadMore() }
        }
        mBinding.rvCoinDetail.apply {
            adapter = mAdapter
            requireContext().dividerBuilder()
                .color(ThemeHelper.getColorByAttr(R.attr.app_divider_color))
                .size(1)
                .insets(DIVIDER_INSET, DIVIDER_INSET)
                .build()
                .addTo(this)
        }
    }

    override fun initViewObserver(viewLifecycleOwner: LifecycleOwner) {
        super.initViewObserver(viewLifecycleOwner)
        observeWithViewLifecycle(mViewModel.pageState) {
            LogUtils.d("${this.javaClass.simpleName} Page.V initArticleObserver $it")
            when (it) {
                is BasePageVM.PageState.Complete -> {
                    val success = it.errorMsg.isEmpty()
                    if (it.refresh) mBinding.refresh.finishRefresh(0, success, it.noMoreData)
                    else mBinding.refresh.finishLoadMore(0, success, it.noMoreData)
                    if (success) {
                        mAdapter.submitList(mViewModel.mCoinList.toList())
                    }
                }
                else -> {
                }
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mBinding.refresh.autoRefresh()
    }
}