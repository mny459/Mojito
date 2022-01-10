package com.mny.wan.pkg.presentation.main.system

import androidx.lifecycle.*
import com.mny.mojito.http.MojitoResult
import com.mny.mojito.http.doSuccess
import com.mny.mojito.mvvm.BaseNoStateViewModel
import com.mojito.common.data.remote.model.*
import com.mny.wan.pkg.domain.usecase.SystemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SystemTagViewModel @Inject constructor(private val mUseCase: SystemUseCase) :
    BaseNoStateViewModel() {

    private var mTag = SystemTagFragment.TAG_SYSTEM
    private val mSystemTree = MutableStateFlow<List<BeanSystemParent>>(emptyList())
    val systemTree: StateFlow<List<BeanSystemParent>> = mSystemTree
    private val mNavTree = MutableStateFlow<List<BeanNav>>(emptyList())
    val navTree: StateFlow<List<BeanNav>> = mNavTree

    fun initTag(tag: Int) {
        this.mTag = tag
    }

    override fun onLoadData() {
        super.onLoadData()
        if (mTag == SystemTagFragment.TAG_SYSTEM) {
            mUseCase.fetchSystemTree()
                .onEach { result ->
                    result.doSuccess {
                        mSystemTree.value = it!!
                    }
                }
                .launchIn(viewModelScope)
        } else {
            mUseCase.fetchNavTree()
                .onEach { result ->
                    result.doSuccess {
                        mNavTree.value = it!!
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    fun loadDataWithCache() {
        if (mTag == SystemTagFragment.TAG_SYSTEM) {
            mUseCase.fetchSystemTreeCache()
                .onEach { result ->
                    result.doSuccess {
                        mSystemTree.value = it!!
                    }
                }
                .launchIn(viewModelScope)
        } else {
            mUseCase.fetchNavTreeCache()
                .onEach { result ->
                    result.doSuccess {
                        mNavTree.value = it!!
                    }
                }
                .launchIn(viewModelScope)
        }
    }

}