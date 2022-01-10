package com.mny.wan.pkg.presentation.main.project

import androidx.lifecycle.viewModelScope
import com.mny.mojito.http.doSuccess
import com.mny.mojito.mvvm.BaseNoStateViewModel
import com.mojito.common.data.remote.model.*
import com.mny.wan.pkg.domain.usecase.ProjectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(private val mUseCase: ProjectUseCase) :
    BaseNoStateViewModel() {

    private val mTabs = MutableStateFlow<List<BeanProject>>(emptyList())
    val tabs: StateFlow<List<BeanProject>> = mTabs

    override fun onLoadData() {
        super.onLoadData()
        mUseCase.fetchProjectTree()
            .onEach { result ->
                result.doSuccess {
                    mTabs.value = it!!
                }
            }
            .launchIn(viewModelScope)
    }
}