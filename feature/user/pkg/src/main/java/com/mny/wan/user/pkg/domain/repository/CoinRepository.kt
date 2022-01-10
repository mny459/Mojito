package com.mny.wan.user.pkg.domain.repository

import com.mojito.common.data.remote.model.*

interface CoinRepository {
    suspend fun fetchCoinInfo(): BaseResponse<BeanCoin>
    suspend fun fetchCoinList(page: Int): BaseResponse<BaseListData<BeanCoinOpDetail>>
    suspend fun fetchCoinRankList(page: Int): BaseResponse<BaseListData<BeanRanking>>
}