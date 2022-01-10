package com.mny.wan.pkg.domain.usecase

import com.mny.mojito.entension.useCase
import com.mny.mojito.http.MojitoResult
import com.mojito.common.data.remote.model.*
import com.mny.wan.pkg.domain.repository.WanRepository
import com.mny.wan.pkg.data.local.WanCache
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SystemUseCase @Inject constructor(
    private val mRepository: WanRepository,
    private val mWanCache: WanCache
) {

    fun fetchSystemTree(): Flow<MojitoResult<List<BeanSystemParent>>> {
        return flow {
            val response = mRepository.fetchSystemTree()
            if (response.isSuccess()) {
                mWanCache.put(WanCache.CacheKey.KNOWLEDGE_LIST, response.data)
                emit(response.toSuccessResult())
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }

    fun fetchSystemTreeCache(): Flow<MojitoResult<List<BeanSystemParent>>> {
        return flow {
            val response =
                mWanCache.getList(WanCache.CacheKey.KNOWLEDGE_LIST, BeanSystemParent::class.java)
            emit(MojitoResult.Success(response))
        }.useCase()
    }

    fun fetchNavTree(): Flow<MojitoResult<List<BeanNav>>> {
        return flow {
            val response = mRepository.fetchNavTree()
            if (response.isSuccess()) {
                mWanCache.put(WanCache.CacheKey.NAVI_LIST, response.data)
                emit(response.toSuccessResult())
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }

    fun fetchNavTreeCache(): Flow<MojitoResult<List<BeanNav>>> {
        return flow {
            val response =
                mWanCache.getList(WanCache.CacheKey.NAVI_LIST, BeanNav::class.java)
            emit(MojitoResult.Success(response))
        }.useCase()
    }
}