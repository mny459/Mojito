package com.mny.wan.pkg.domain.usecase

import com.mny.mojito.error.RemoteError
import com.mny.mojito.error.UseCaseError
import javax.inject.Inject
import com.mny.mojito.http.MojitoResult
import com.mojito.common.data.local.UserHelper
import com.mojito.common.data.remote.model.*
import com.mny.mojito.entension.useCase
import com.mny.wan.pkg.domain.repository.WanRepository
import kotlinx.coroutines.flow.*

/**
 * Desc:
 */
class UserUseCase @Inject constructor(private val mRepository: WanRepository) {

//    fun fetchCoinInfo(): Flow<MojitoResult<BeanCoin>> {
//        return flow {
//            if (UserHelper.isLogin()) {
//                val response = mRepository.fetchCoinInfo()
//                if (response.isSuccess()) {
//                    emit(response.toSuccessResult())
//                } else {
//                    emit(response.toErrorResult())
//                }
//            } else {
//                emit(MojitoResult.Error(RemoteError(-1, "请先登录")))
//            }
//        }.useCase()
//    }

    fun hasLogin() = UserHelper.isLogin()
}