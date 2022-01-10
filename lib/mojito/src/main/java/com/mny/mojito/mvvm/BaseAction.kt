package com.mny.mojito.mvvm

interface BaseAction

sealed class BaseViewAction : BaseAction {
    object Loading : BaseViewAction()
    class Complete(val errorMsg: String = "") : BaseViewAction()
}

abstract class BaseNoStateViewModel :
    BaseViewModel<BaseNoStateViewModel.ViewState, BaseNoStateViewModel.Action>(ViewState) {
    object ViewState : BaseState
    object Action : BaseAction

    override fun onReduceState(viewAction: Action): ViewState = ViewState
}