package com.mny.wan.user.pkg.data.repository

import com.mny.wan.user.pkg.data.remote.service.UserService
import com.mny.wan.user.pkg.domain.repository.UserRepository
import com.mojito.common.data.remote.model.BaseResponse
import com.mojito.common.data.remote.model.BeanCoin
import com.mojito.common.data.remote.model.BeanUserInfo
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val mWanService: UserService) :
    UserRepository {

    override suspend fun login(username: String, password: String): BaseResponse<BeanUserInfo> =
        mWanService.login(username, password)

    override suspend fun register(
        username: String,
        password: String,
        rePassword: String
    ): BaseResponse<BeanUserInfo> = mWanService.register(username, password, rePassword)

    override suspend fun logout(): BaseResponse<String> = mWanService.logout()

    override suspend fun fetchCoinInfo(): BaseResponse<BeanCoin> = mWanService.coinInfo()
}