package com.mny.wan.pkg.presentation.main.wechat

import androidx.lifecycle.viewModelScope
import com.mny.mojito.http.MojitoResult
import com.mny.mojito.mvvm.BaseNoStateViewModel
import com.mojito.common.data.remote.model.*
import com.mny.wan.pkg.domain.usecase.WeChatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeChatViewModel @Inject constructor(private val mUseCase: WeChatUseCase) :
    BaseNoStateViewModel() {

    private val mTabs = MutableStateFlow<List<BeanSystemParent>>(emptyList())
    val tabs: StateFlow<List<BeanSystemParent>> = mTabs

    override fun onLoadData() {
        super.onLoadData()
        mUseCase.fetchWeChatTree()
            .onEach { result ->
                when (result) {
                    is MojitoResult.Success -> {
                        result.data?.apply {
                            mTabs.value = this
                        }
                    }
                    else -> {
                    }
                }
            }
            .launchIn(viewModelScope)
    }

}