package com.mny.mojito.eyepetizer.pkg.presentation

import androidx.lifecycle.viewModelScope
import com.mny.mojito.mvvm.BasePageVM
import com.mny.mojito.eyepetizer.pkg.data.UrlManager
import com.mny.mojito.eyepetizer.pkg.data.remote.model.EyepetizerDialy
import com.mny.mojito.eyepetizer.pkg.data.remote.model.Item
import com.mny.mojito.eyepetizer.pkg.data.remote.model.ItemData
import com.mny.mojito.eyepetizer.pkg.domain.usecase.EyepetizerUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * EyepetizerViewModel
 * Desc:
 */
@HiltViewModel
class EyepetizerViewModel @Inject constructor(val mUseCase: EyepetizerUsecase) :
    BasePageVM<EyepetizerDialy>() {

    private var mNextPage = UrlManager.DAILY_URL
    val mItems = mutableListOf<Item>()

    override fun refresh() {
        mNextPage = UrlManager.DAILY_URL
        super.refresh()
    }

    override fun onLoadData() {
        super.onLoadData()
        if (mNextPage.isNotEmpty()) {
            mUseCase.fetchUrl(mNextPage)
                .onEach { onDataLoaded(it) }
                .launchIn(viewModelScope)
        }
    }

    override fun onNewData(data: EyepetizerDialy) {
        super.onNewData(data)
        if (isRefresh()) {
            mItems.clear()
        }

        data.itemList.apply {
            mItems.addAll(this)
        }
        mNextPage = data.nextPageUrl
        updateNoMoreData(mNextPage.isEmpty())
    }

    override fun isRefresh(): Boolean = mNextPage == UrlManager.DAILY_URL
}