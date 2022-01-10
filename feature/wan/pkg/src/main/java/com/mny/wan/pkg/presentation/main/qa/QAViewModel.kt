package com.mny.wan.pkg.presentation.main.qa

import androidx.lifecycle.viewModelScope
import com.mny.wan.pkg.base.BaseArticlePageVM
import com.mny.wan.pkg.domain.usecase.ArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class QAViewModel @Inject constructor(private val mUseCase: ArticleUseCase) :
    BaseArticlePageVM() {

    override var initPage: Int = 1

    override fun initWithCache() {
        super.initWithCache()
        mUseCase.fetchQACacheArticle(initPage)
            .onEach { onDataLoaded(it) }
            .launchIn(viewModelScope)
    }

    override fun onLoadData() {
        super.onLoadData()
        mUseCase.fetchQAArticle(mCurPage)
            .onEach { onDataLoaded(it) }
            .launchIn(viewModelScope)
    }
}