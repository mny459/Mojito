package com.mny.wan.pkg.domain.usecase

import com.mny.mojito.entension.useCase
import com.mny.mojito.http.MojitoResult
import com.mny.wan.pkg.data.UrlManager
import com.mny.wan.pkg.domain.repository.WanRepository
import com.mny.wan.pkg.data.local.WanCache
import com.mojito.common.data.remote.model.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val mRepository: WanRepository,
    private val mWanCache: WanCache
) {

    fun fetchBannerList(): Flow<MojitoResult<List<BeanBanner>>> {
        return flow {
            val list = mWanCache.getList(WanCache.CacheKey.BANNER, BeanBanner::class.java)
            if (list.isNotEmpty()) {
                emit(MojitoResult.Success(list))
            }
            val response = mRepository.fetchBannerList()
            if (response.isSuccess()) {
                if (!mWanCache.isSame(list, response.data)) {
                    mWanCache.put(WanCache.CacheKey.BANNER, response.data)
                    emit(response.toSuccessResult())
                }
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }

    fun fetchTopArticles(): Flow<MojitoResult<List<BeanArticle>>> {
        return flow {
            val list = try {
                mWanCache.getList(WanCache.CacheKey.TOP_ARTICLE_LIST, BeanArticle::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
            if (list.isNotEmpty()) {
                emit(MojitoResult.Success(list))
            }
            val response = mRepository.fetchTopArticles()
            if (response.isSuccess()) {
                if (!mWanCache.isSame(list, response.data)) {
                    mWanCache.put(WanCache.CacheKey.TOP_ARTICLE_LIST, response.data)
                    emit(response.toSuccessResult())
                }
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }

    fun fetchHomeArticles(page: Int): Flow<MojitoResult<BeanArticleList>> {
        return flow {
            val response = mRepository.fetchArticlesByUrl(UrlManager.urlHomeArticleList(page))
            if (response.isSuccess()) {
                if (page == 0) mWanCache.put(WanCache.CacheKey.ARTICLE_LIST(page), response.data)
                emit(response.toSuccessResult())
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }

    fun fetchHomeCacheArticles(page: Int): Flow<MojitoResult<BeanArticleList>> {
        return flow {
            val response =
                mWanCache.get(WanCache.CacheKey.ARTICLE_LIST(page), BeanArticleList::class.java)
            emit(MojitoResult.Success(response))
        }.useCase()
    }


}