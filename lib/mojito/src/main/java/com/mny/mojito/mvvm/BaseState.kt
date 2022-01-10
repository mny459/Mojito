package com.mny.mojito.mvvm

interface BaseState
interface BaseDataState
data class BaseViewState(
    val loading: Boolean = false,
    val complete: Boolean = false,
    val errorMsg: String = ""
) : BaseState

