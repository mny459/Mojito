package com.mny.wan.pkg.domain.usecase

import com.mny.mojito.entension.useCase
import com.mny.mojito.http.MojitoResult
import com.mojito.common.data.remote.model.*
import com.mny.wan.pkg.domain.repository.WanRepository
import com.mny.wan.pkg.data.local.WanCache
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProjectUseCase @Inject constructor(
    private val mRepository: WanRepository,
    private val mWanCache: WanCache
) {

    fun fetchProjectTree(): Flow<MojitoResult<List<BeanProject>>> {
        return flow {
            val list =
                mWanCache.getList(WanCache.CacheKey.PROJECT_CHAPTERS, BeanProject::class.java)
            if (list.isNotEmpty()) {
                emit(MojitoResult.Success(list))
            }
            val response = mRepository.fetchProjectTree()
            if (response.isSuccess()) {
                if (!mWanCache.isSame(list, response.data)) {
                    mWanCache.put(WanCache.CacheKey.PROJECT_CHAPTERS, response.data)
                    emit(response.toSuccessResult())
                }
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }

}