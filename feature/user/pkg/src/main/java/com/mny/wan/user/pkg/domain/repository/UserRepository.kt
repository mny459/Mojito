package com.mny.wan.user.pkg.domain.repository

import com.mojito.common.data.remote.model.BaseResponse
import com.mojito.common.data.remote.model.BeanCoin
import com.mojito.common.data.remote.model.BeanUserInfo

interface UserRepository {

    suspend fun login(username: String, password: String): BaseResponse<BeanUserInfo>

    suspend fun register(
        username: String,
        password: String,
        rePassword: String
    ): BaseResponse<BeanUserInfo>

    suspend fun logout(): BaseResponse<String>

    suspend fun fetchCoinInfo(): BaseResponse<BeanCoin>
}