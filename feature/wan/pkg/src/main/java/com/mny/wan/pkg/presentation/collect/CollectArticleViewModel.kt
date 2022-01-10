package com.mny.wan.pkg.presentation.collect

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
class CollectArticleViewModel @Inject constructor(private val mUseCase: ArticleUseCase) :
    BaseArticlePageVM() {

    override var initPage: Int = 1
    override fun initWithCache() {
        super.initWithCache()
        mUseCase.fetchCollectCacheArticle(mCurPage)
            .onEach {
                onDataLoaded(it)
            }
            .launchIn(viewModelScope)
    }

    override fun onLoadData() {
        super.onLoadData()
        mUseCase.fetchCollectArticle(mCurPage)
            .onEach {
                onDataLoaded(it)
            }
            .launchIn(viewModelScope)
    }
}