package com.mny.mojito.mvvm

/**
 * Desc:
 */
abstract class BaseStateViewModel : BaseViewModel<BaseViewState, BaseViewAction>(BaseViewState()) {
    override fun onReduceState(viewAction: BaseViewAction): BaseViewState =
        onReduceViewState(viewAction)
}