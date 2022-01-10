package com.mny.wan.user.pkg.data.repository

import com.mny.wan.user.pkg.data.remote.service.UserService
import com.mny.wan.user.pkg.domain.repository.CoinRepository
import com.mny.wan.user.pkg.domain.repository.UserRepository
import com.mojito.common.data.remote.model.*
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(private val mWanService: UserService) :
    CoinRepository {

    override suspend fun fetchCoinInfo(): BaseResponse<BeanCoin> = mWanService.coinInfo()

    override suspend fun fetchCoinList(page: Int): BaseResponse<BaseListData<BeanCoinOpDetail>> =
        mWanService.coinList(page)

    override suspend fun fetchCoinRankList(page: Int): BaseResponse<BaseListData<BeanRanking>> =
        mWanService.rankList(page)
}