package com.mny.mojito.status

/**
 * UI 状态管理：数据加载中、加载成功、加载出错、加载完成、空
 * Status
 * @author crj
 * Created on 2020-07-23 16:54
 */
sealed class UIStatus<out T : Any> {
    object UINone : UIStatus<Nothing>()
    object UILoading : UIStatus<Nothing>()
    object UIEmpty : UIStatus<Nothing>()
    data class UIData<out DATA : Any>(val data: DATA) : UIStatus<DATA>()
    data class UIError(val errMsg: String) : UIStatus<Nothing>()
    object UIComplete : UIStatus<Nothing>()
}