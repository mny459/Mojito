package com.mny.wan.pkg.presentation.main.system.secondsystem

import androidx.lifecycle.viewModelScope
import com.mny.wan.pkg.base.BaseArticlePageVM
import com.mny.wan.pkg.domain.usecase.ArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SystemChildrenArticleViewModel @Inject constructor(private val mUseCase: ArticleUseCase) :
    BaseArticlePageVM() {

    private var mCId: Int = 0

    fun initCId(cId: Int) {
        this.mCId = cId
    }

    override fun initWithCache() {
        super.initWithCache()
        mUseCase.fetchSystemCacheArticle(mCurPage, mCId)
            .onEach { onDataLoaded(it) }
            .launchIn(viewModelScope)
    }

    override fun onLoadData() {
        super.onLoadData()
        mUseCase.fetchSystemArticle(mCurPage, mCId)
            .onEach { onDataLoaded(it) }
            .launchIn(viewModelScope)
    }

}