package com.mny.wan.user.pkg.presentation.coin

import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.mvvm.BasePageVM
import com.mojito.common.data.remote.model.*
import com.mny.wan.user.pkg.domain.usecase.CoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CoinRankViewModel @Inject constructor(
    private val mUseCase: CoinUseCase
) : BasePageVM<BaseListData<BeanRanking>>() {

    override var initPage: Int = 1

    val mRankList = mutableListOf<BeanRanking>()

    override fun onLoadData() {
        super.onLoadData()
        mUseCase.fetchRankList(mCurPage)
            .onEach {
                onDataLoaded(it)
            }
            .launchIn(viewModelScope)
    }

    override fun onNewData(data: BaseListData<BeanRanking>) {
        super.onNewData(data)
        LogUtils.v("${this.javaClass.simpleName} Page.VM onNewData $data ")
        if (isRefresh()) {
            mRankList.clear()
        }
        data.data.apply {
            mRankList.addAll(this)
        }
        // mCurPage = data.curPage
        updateNoMoreData(data.over && data.offset >= data.total)
    }

}