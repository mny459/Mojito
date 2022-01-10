package com.mny.wan.pkg.presentation.main.home

import androidx.lifecycle.*
import com.mny.mojito.http.MojitoResult
import com.mny.wan.pkg.base.BaseArticlePageVM
import com.mojito.common.data.remote.model.*
import com.mny.wan.pkg.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val mUseCase: HomeUseCase) : BaseArticlePageVM() {

    private val mBannerList = MutableStateFlow<List<BeanBanner>>(emptyList())
    val bannerList: StateFlow<List<BeanBanner>> = mBannerList
    private val mTopArticles = MutableStateFlow<List<BeanArticle>>(emptyList())
    val topArticles: StateFlow<List<BeanArticle>> = mTopArticles

    override fun initWithCache() {
        super.initWithCache()
        viewModelScope.launch {
            launch {
                fetchBannerList()
                fetchTopArticles()
                mUseCase.fetchHomeCacheArticles(mCurPage)
                    .collect {
                        onDataLoaded(it)
                    }
            }
        }
    }

    override fun onLoadData() {
        super.onLoadData()
        viewModelScope.launch {
            launch {
                if (isRefresh()) {
                    fetchBannerList()
                    fetchTopArticles()
                }
                fetchArticleList()
            }
        }
    }

    private suspend fun fetchTopArticles() = withContext(Dispatchers.IO) {
        mUseCase.fetchTopArticles()
            .collect {
                when (it) {
                    is MojitoResult.Success -> {
                        it.data?.apply {
                            mTopArticles.value = this
                        }
                    }
                    else -> {
                    }
                }
            }
    }


    private suspend fun fetchBannerList() = withContext(Dispatchers.IO) {
        mUseCase.fetchBannerList()
            .collect {
                when (it) {
                    is MojitoResult.Success -> {
                        it.data?.apply {
                            mBannerList.value = this
                        }
                    }
                    else -> {
                    }
                }
            }
    }

    private suspend fun fetchArticleList() = withContext(Dispatchers.IO) {
        mUseCase.fetchHomeArticles(mCurPage)
            .collect {
                onDataLoaded(it)
            }
    }
}