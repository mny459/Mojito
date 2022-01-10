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
class CoinDetailViewModel @Inject constructor(private val mUseCase: CoinUseCase) :
    BasePageVM<BaseListData<BeanCoinOpDetail>>() {

    override var initPage: Int = 1
    val mCoinList = mutableListOf<BeanCoinOpDetail>()

    override fun onLoadData() {
        super.onLoadData()
        mUseCase.fetchCoinList(mCurPage)
            .onEach {
                onDataLoaded(it)
            }
            .launchIn(viewModelScope)
    }

    override fun onNewData(data: BaseListData<BeanCoinOpDetail>) {
        super.onNewData(data)
        LogUtils.d("${this.javaClass.simpleName} Page.VM onNewData $data ")
        if (isRefresh()) {
            mCoinList.clear()
        }
        data.data.apply {
            mCoinList.addAll(this)
        }
        // mCurPage = data.curPage
        updateNoMoreData(data.over && data.offset >= data.total)
    }

}