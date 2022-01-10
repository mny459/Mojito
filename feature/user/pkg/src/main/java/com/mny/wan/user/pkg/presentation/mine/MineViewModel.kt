package com.mny.wan.user.pkg.presentation.mine

import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.entension.showToast
import com.mny.mojito.http.MojitoResult
import com.mny.mojito.mvvm.*
import com.mojito.common.data.local.UserHelper
import com.mny.wan.user.pkg.domain.model.UserInfoDomain
import com.mny.wan.user.pkg.domain.usecase.CoinUseCase
import com.mny.wan.user.pkg.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MineViewModel @Inject constructor(
    private val mUserUseCase: UserUseCase,
    private val mCoinUseCase: CoinUseCase
) :
    BaseNoStateViewModel() {

    val userInfo = combineTransform(UserHelper.userFlow, UserHelper.userCoinFlow) { user, coin ->
        val isLogin = user != null
        val nickname = user?.nickname ?: ""
        var level = 0
        var rank = 0
        if (coin != null) {
            level = coin.level
            rank = coin.rank
        } else if (isLogin) {
            updateUserInfo()
        }
        val userInfo = UserInfoDomain(isLogin, nickname, rank, level)
        LogUtils.d("Mine.combineTransform $userInfo")
        emit(userInfo)
    }

    fun updateUserInfo() {
        if (UserHelper.isLogin()) {
            mCoinUseCase.fetchCoinInfo()
                .launchIn(viewModelScope)
        }
    }

    fun logout() {
        mUserUseCase.logout()
            .launchIn(viewModelScope)
    }

}