package com.mny.wan.pkg.domain.usecase

import com.mny.mojito.entension.useCase
import com.mny.mojito.http.MojitoResult
import com.mny.wan.pkg.domain.repository.WanRepository
import com.mny.wan.pkg.data.local.WanCache
import com.mojito.common.data.remote.model.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class WeChatUseCase @Inject constructor(
    private val mRepository: WanRepository,
    private val mWanCache: WanCache
) {

    fun fetchWeChatTree(): Flow<MojitoResult<List<BeanSystemParent>>> {
        return flow {
            val list =
                mWanCache.getList(
                    WanCache.CacheKey.WX_ARTICLE_CHAPTERS,
                    BeanSystemParent::class.java
                )
            if (list.isNotEmpty()) {
                emit(MojitoResult.Success(list))
            }
            val response = mRepository.fetchWeChatTree()
            if (response.isSuccess()) {
                if (!mWanCache.isSame(list, response.data)) {
                    mWanCache.put(WanCache.CacheKey.WX_ARTICLE_CHAPTERS, response.data)
                    emit(response.toSuccessResult())
                }
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }

}