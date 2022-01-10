package com.mny.wan.pkg.presentation.search

import androidx.lifecycle.*
import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.http.doSuccess
import com.mny.wan.pkg.base.BaseArticlePageVM
import com.mojito.common.data.remote.model.*
import com.mny.wan.pkg.domain.usecase.ArticleUseCase
import com.mny.wan.pkg.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mUseCase: ArticleUseCase,
    private val mSearchUseCase: SearchUseCase,
    private val mSavedStateHandle: SavedStateHandle
) :
    BaseArticlePageVM() {

    companion object {
        const val KEY_ARTICLE = "search"
    }

    private val mHotKey = MutableStateFlow<List<BeanHotKey>>(emptyList())
    val hotKey: StateFlow<List<BeanHotKey>> = mHotKey

    private var mSearchJob: Job? = null
    private val mSearchStateFlow = MutableStateFlow("")
    val searchKey: StateFlow<String> = mSearchStateFlow

    fun observerSearchKeys() {
        viewModelScope.launch {
            mSearchStateFlow
                .debounce(200)
                .collectLatest {
                    LogUtils.d("搜索 $it")
                    mSavedStateHandle.set(KEY_ARTICLE, it)
                }
        }
    }

    fun fetchHotKey() {
        mSearchUseCase.fetchHotKey()
            .onEach { result ->
                result.doSuccess { data ->
                    data?.apply {
                        mHotKey.value = this
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun search(name: String) {
        if (mSearchStateFlow.value == name) return
        mSearchJob?.cancel()
        mSearchStateFlow.value = name
    }

    override fun onLoadData() {
        super.onLoadData()
        mUseCase.fetchSearchArticle(mCurPage, mSavedStateHandle[KEY_ARTICLE] ?: "")
            .onEach {
                onDataLoaded(it)
            }
            .launchIn(viewModelScope)
    }
}