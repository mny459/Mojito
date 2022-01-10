package com.mny.mojito.mvvm

import com.blankj.utilcode.util.LogUtils
import com.kunminx.architecture.ui.callback.ProtectedUnPeekLiveData
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.mny.mojito.http.MojitoResult

/**
 * 分页
 */
abstract class BasePageVM<DATA : Any> : BaseNoStateViewModel() {
    protected open var initPage = 0
    protected var mCurPage = 0
    private var mNoMoreData = false
    private val mPageState = UnPeekLiveData<PageState>()
    val pageState: ProtectedUnPeekLiveData<PageState> = mPageState
    open fun initWithCache() {}
    open fun refresh() {
        mCurPage = initPage
        updateNoMoreData(false)
        loadData()
    }

    open fun loadMore() {
        if (mNoMoreData) return
        mCurPage += 1
        loadData()
    }

    protected fun updateNoMoreData(noMore: Boolean) {
        this.mNoMoreData = noMore
    }

    protected fun onDataLoaded(result: MojitoResult<DATA>) {
        when (result) {
            is MojitoResult.Error -> {
                // TODO
                sendPageState(PageState.Complete(isRefresh(), false, result.exception.msg))
            }
            MojitoResult.Loading -> {
                sendPageState(PageState.Loading(isRefresh()))
            }
            is MojitoResult.Success -> {
                result.data?.apply { onNewData(this) }
                sendPageState(PageState.Complete(isRefresh(), mNoMoreData, ""))
            }
            else -> {
            }
        }
    }

    protected open fun onNewData(data: DATA) {}
    protected open fun isRefresh() = mCurPage == initPage
    sealed class PageState {
        data class Loading(val refresh: Boolean = true) : PageState()
        data class Complete(
            val refresh: Boolean = true,
            val noMoreData: Boolean = false,
            val errorMsg: String = ""
        ) : PageState()
    }

    private fun sendPageState(state: PageState) {
        LogUtils.v("${this.javaClass.simpleName} Page.VM sendPageState $state curPage = $mCurPage initPage = $initPage noMoreData = $mNoMoreData")
        mPageState.postValue(state)
    }
}