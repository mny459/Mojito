package com.mny.wan.user.pkg.domain.usecase

import com.mny.mojito.entension.showToast
import com.mny.mojito.error.UseCaseError
import com.mny.wan.user.pkg.domain.repository.UserRepository
import javax.inject.Inject
import com.mny.mojito.http.MojitoResult
import com.mny.mojito.entension.useCase
import com.mojito.common.data.local.UserHelper
import com.mojito.common.data.remote.model.BeanCoin
import com.mojito.common.data.remote.model.BeanUserInfo
import com.mojito.common.data.remote.model.toErrorResult
import com.mojito.common.data.remote.model.toSuccessResult
import kotlinx.coroutines.flow.*

class UserUseCase @Inject constructor(private val mRepository: UserRepository) {

    fun login(username: String, password: String): Flow<MojitoResult<BeanUserInfo>> {
        return flow {
            val response = mRepository.login(username, password)
            if (response.isSuccess()) {
                val userInfo = response.data
                UserHelper.login(userInfo)
                emit(MojitoResult.Success(userInfo))
            } else {
                emit(MojitoResult.Error(UseCaseError(response.errorCode, response.errorMsg)))
            }
        }.useCase()
    }

    fun register(
        username: String,
        password: String,
        rePassword: String
    ): Flow<MojitoResult<BeanUserInfo>> {
        return flow {
            val response = mRepository.register(username, password, rePassword)
            if (response.isSuccess()) {
                val userInfo = response.data
                UserHelper.login(userInfo)
                emit(response.toSuccessResult())
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }

    fun logout(): Flow<MojitoResult<String>> {
        return flow {
            val response = mRepository.logout()
            UserHelper.loginOut()
            "退出登录成功".showToast()
            if (response.isSuccess()) {
                emit(response.toSuccessResult())
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }

    fun fetchCoinInfo(): Flow<MojitoResult<BeanCoin>> {
        return flow {
            val response = mRepository.fetchCoinInfo()
            if (response.isSuccess()) {
                UserHelper.userCoin = response.data
                emit(response.toSuccessResult())
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }

}