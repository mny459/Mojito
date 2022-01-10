package com.mojito.common.webview


import com.mny.mojito.mvvm.BaseAction
import com.mny.mojito.mvvm.BaseState
import com.mny.mojito.mvvm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor() :
    BaseViewModel<WebViewViewModel.ViewState, WebViewViewModel.Action>(ViewState.InitUrl("")) {

    fun initUrl(url: String) {
        sendAction(Action.InitUrl(url))
    }

    fun updateTitle(title: String) {
        sendAction(Action.UpdateTitle(title))
    }

    sealed class ViewState : BaseState {
        class InitUrl(val url: String) : ViewState()
        class Title(val title: String) : ViewState()
    }

    sealed class Action : BaseAction {
        class InitUrl(val url: String) : Action()
        class UpdateTitle(val title: String) : Action()
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction) {
        is Action.InitUrl -> ViewState.InitUrl(viewAction.url)
        is Action.UpdateTitle -> ViewState.Title(viewAction.title)
    }

}