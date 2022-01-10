package com.mny.wan.user.pkg.presentation.login

import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.http.MojitoResult
import com.mny.mojito.mvvm.BaseNoStateViewModel
import com.mny.mojito.status.UIStatus
import com.mny.wan.user.pkg.domain.usecase.UserUseCase
import com.mojito.common.helper.hideLoading
import com.mojito.common.helper.showLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
//    private val mAppViewModel: AppViewModel,
    private val mUserUseCase: UserUseCase
) : BaseNoStateViewModel() {

    private val mLoginState = MutableStateFlow<UIStatus<Any>>(UIStatus.UINone)
    val loginState: StateFlow<UIStatus<Any>> = mLoginState

    fun login(username: String, password: String) {
        mUserUseCase.login(username, password)
            .onEach {
                when (it) {
                    is MojitoResult.Success -> {
                        mLoginState.value = UIStatus.UIData("")
                    }
                    is MojitoResult.Error -> {
                        mLoginState.value = UIStatus.UIError(it.exception.msg)
                    }
                    MojitoResult.Loading -> {
                        mLoginState.value = UIStatus.UILoading
                        showLoading()
                    }
                    MojitoResult.Complete -> {
                        hideLoading()
                    }
                    else -> {
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun register(
        username: String,
        password: String,
        rePassword: String
    ) {
        mUserUseCase.register(username, password, rePassword)
            .onEach {
                when (it) {
                    is MojitoResult.Success -> {
                        mLoginState.value = UIStatus.UIData("")
                    }
                    is MojitoResult.Error -> {
                        mLoginState.value = UIStatus.UIError(it.exception.msg)
                    }
                    MojitoResult.Loading -> {
                        mLoginState.value = UIStatus.UILoading
                        showLoading()
                    }
                    MojitoResult.Complete -> {
                        hideLoading()
                    }
                    else -> {
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}
