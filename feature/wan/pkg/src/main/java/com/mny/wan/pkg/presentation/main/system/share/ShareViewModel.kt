package com.mny.wan.pkg.presentation.main.system.share

import androidx.lifecycle.*
import com.mny.wan.pkg.base.BaseArticlePageVM
import com.mny.wan.pkg.domain.usecase.ArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 广场
 */
@HiltViewModel
class ShareViewModel @Inject constructor(private val mUseCase: ArticleUseCase) :
    BaseArticlePageVM() {

    override fun initWithCache() {
        super.initWithCache()
        mUseCase.fetchShareCacheArticle(mCurPage)
            .onEach { onDataLoaded(it) }
            .launchIn(viewModelScope)
    }

    override fun onLoadData() {
        super.onLoadData()
        mUseCase.fetchShareArticle(mCurPage)
            .onEach { onDataLoaded(it) }
            .launchIn(viewModelScope)
    }
}