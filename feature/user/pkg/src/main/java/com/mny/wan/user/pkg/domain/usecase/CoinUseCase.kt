package com.mny.wan.user.pkg.domain.usecase

import com.mny.mojito.entension.useCase
import com.mny.mojito.http.MojitoResult
import com.mny.wan.user.pkg.domain.repository.CoinRepository
import com.mojito.common.data.local.UserHelper
import com.mojito.common.data.remote.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * CoinUseCase
 * @author caicai
 * Created on 2021-11-01 15:27
 * Desc:
 */
class CoinUseCase @Inject constructor(private val mRepository: CoinRepository) {

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

    fun fetchCoinList(page: Int): Flow<MojitoResult<BaseListData<BeanCoinOpDetail>>> {
        return flow {
            val response = mRepository.fetchCoinList(page)
            if (response.isSuccess()) {
                emit(response.toSuccessResult())
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }

    fun fetchRankList(page: Int): Flow<MojitoResult<BaseListData<BeanRanking>>> {
        return flow {
            val response = mRepository.fetchCoinRankList(page)
            if (response.isSuccess()) {
                emit(response.toSuccessResult())
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }
}