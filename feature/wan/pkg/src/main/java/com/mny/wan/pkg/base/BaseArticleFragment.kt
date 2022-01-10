package com.mny.wan.pkg.base

import android.os.Bundle
import androidx.appcompat.widget.ThemeUtils
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.fondesa.recyclerviewdivider.Divider
import com.fondesa.recyclerviewdivider.Grid
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.fondesa.recyclerviewdivider.inset.InsetProvider
import com.mny.mojito.entension.observeWithViewLifecycle
import com.mny.mojito.mvvm.BasePageVM
import com.mny.wan.pkg.presentation.adapter.ArticleAdapter
import com.mojito.base.BaseImmersionVBFragment
import com.mojito.common.R
import com.mojito.common.helper.ThemeHelper
import com.mojito.common.holderview.HolderViewHelper
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import javax.inject.Inject

abstract class BaseArticleFragment<VB : ViewBinding> : BaseImmersionVBFragment<VB>() {

    protected var mRvArticles: RecyclerView? = null
    protected var mRefresh: SmartRefreshLayout? = null

    @Inject
    protected lateinit var mAdapter: ArticleAdapter

    @Inject
    protected lateinit var mHolderHelper: HolderViewHelper
    override fun initViewObserver(viewLifecycleOwner: LifecycleOwner) {
        super.initViewObserver(viewLifecycleOwner)
        initArticleObserver()
    }

    fun initRefreshAndRv(refresh: SmartRefreshLayout, rvArticles: RecyclerView) {
        // mHolderHelper.initOriginView(refresh)
        mRefresh = refresh
        mRvArticles = rvArticles
        with(refresh) {
            setOnRefreshListener { articleViewModel().refresh() }
            setOnLoadMoreListener { articleViewModel().loadMore() }
        }
        rvArticles.apply {
            mAdapter.viewLifecycleOwner = viewLifecycleOwner
            adapter = mAdapter
            requireContext().dividerBuilder()
                .color(ThemeHelper.getColorByAttr(R.attr.app_divider_color))
                .size(1)
                .insetProvider(INSET_PROVIDER)
                .build()
                .addTo(this)
        }
    }

    private fun initArticleObserver() {
        observeWithViewLifecycle(articleViewModel().pageState) {
            LogUtils.d("${this.javaClass.simpleName} Page.V initArticleObserver $it refreshIsEmpty = ${mRefresh == null}")
            when (it) {
                is BasePageVM.PageState.Complete -> {
                    val success = it.errorMsg.isEmpty()
                    if (it.refresh) mRefresh?.finishRefresh(0, success, it.noMoreData)
                    else mRefresh?.finishLoadMore(0, success, it.noMoreData)
                    if (success) {
                        if (articleViewModel().mArticles.isEmpty()) {
                            // mHolderHelper.showRetry { articleViewModel().refresh() }
                        } else {
                            // mHolderHelper.showOrigin()
                            mAdapter.submitList(articleViewModel().mArticles.toList())
                        }
                    }
                }
                else -> {
                }
            }
        }
    }

    abstract fun articleViewModel(): BaseArticlePageVM

    override fun initImmersionBar() {

    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        articleViewModel().initWithCache()
    }

    override fun onFirstInit() {
        super.onFirstInit()
        articleViewModel().refresh()
    }

    override fun onDestroyView() {
        mRefresh = null
        mRvArticles?.adapter = null
        mRvArticles = null
        super.onDestroyView()
    }

    companion object {
        private val DIVIDER_INSET = SizeUtils.dp2px(16F)
        private val INSET_PROVIDER = object : InsetProvider {
            override fun getDividerInsetEnd(grid: Grid, divider: Divider) = DIVIDER_INSET

            override fun getDividerInsetStart(grid: Grid, divider: Divider) = DIVIDER_INSET
        }
    }
}