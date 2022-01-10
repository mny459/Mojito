package com.mny.wan.pkg.presentation.main.wechat.article

import androidx.lifecycle.viewModelScope
import com.mny.wan.pkg.base.BaseArticlePageVM
import com.mny.wan.pkg.domain.usecase.ArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeChatArticleViewModel @Inject constructor(
    private val mUseCase: ArticleUseCase,
) : BaseArticlePageVM() {
    override var initPage: Int = 1
    private var mCId: Int = 0

    fun initCId(cId: Int) {
        this.mCId = cId
    }

    override fun initWithCache() {
        super.initWithCache()
        mUseCase.fetchWeChatCacheArticle(mCurPage, mCId)
            .onEach { onDataLoaded(it) }
            .launchIn(viewModelScope)
    }

    override fun onLoadData() {
        super.onLoadData()
        mUseCase.fetchWeChatArticle(mCurPage, mCId)
            .onEach { onDataLoaded(it) }
            .launchIn(viewModelScope)
    }
}