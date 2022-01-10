package com.mny.wan.pkg.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mny.mojito.entension.showToast
import com.mny.mojito.http.MojitoResult
import com.mny.mojito.http.doSuccess
import com.mojito.common.data.local.UserHelper
import com.mny.wan.pkg.domain.usecase.CollectUseCase
import com.mny.wan.pkg.domain.usecase.UserUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val mUseCase: CollectUseCase,
    private val mUserUseCase: UserUseCase,
) : AndroidViewModel(context as Application) {

    // 保存原始的 UI 需要展示的用户信息

    private val mCollectIds = MutableStateFlow(0)
    private val mCancelCollectIds = MutableStateFlow(0)
    val collectIds: StateFlow<Int> = mCollectIds
    val cancelCollectIds: StateFlow<Int> = mCancelCollectIds

    fun collect(articleId: Int) {
        mUseCase.collect(articleId)
            .onEach {
                it.doSuccess {
                    mCollectIds.value = articleId
                    mCollectIds.value = 0
                }
            }
            .launchIn(viewModelScope)
    }

    fun cancelCollect(articleId: Int) {
        mUseCase.cancelCollect(articleId)
            .onEach {
                it.doSuccess {
                    mCancelCollectIds.value = articleId
                    mCancelCollectIds.value = 0
                }
            }
            .launchIn(viewModelScope)
    }


}