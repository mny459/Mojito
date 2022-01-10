package com.mny.wan.pkg.domain.usecase

import com.mny.mojito.entension.useCase
import com.mny.mojito.http.MojitoResult
import com.mny.wan.pkg.data.UrlManager
import com.mojito.common.data.remote.model.*
import com.mny.wan.pkg.domain.repository.WanRepository
import com.mny.wan.pkg.data.local.WanCache
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleUseCase @Inject constructor(
    private val mRepository: WanRepository,
    private val mWanCache: WanCache
) {

    fun fetchQAArticle(page: Int): Flow<MojitoResult<BeanArticleList>> {
        return flow {
            val url = UrlManager.qaArticleList(page)
            val response = mRepository.fetchArticlesByUrl(url)
            if (response.isSuccess()) {
                // 只缓存第一页的数据
                if (page == 1) mWanCache.put(WanCache.CacheKey.QUESTION_LIST(page), response.data)
                emit(response.toSuccessResult())
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }

    fun fetchQACacheArticle(page: Int): Flow<MojitoResult<BeanArticleList>> {
        return flow {
            val response = mWanCache.get(
                WanCache.CacheKey.QUESTION_LIST(page),
                BeanArticleList::class.java
            )
            emit(MojitoResult.Success(response))
        }.useCase()
    }

    fun fetchCollectArticle(page: Int): Flow<MojitoResult<BeanArticleList>> {
        return flow {
            val response = mRepository.fetchArticlesByUrl(UrlManager.urlCollectArticleList(page))
            if (response.isSuccess()) {
                if (page == 0) mWanCache.put(
                    WanCache.CacheKey.COLLECT_ARTICLE_LIST(page),
                    response.data
                )
                emit(response.toSuccessResult())
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }

    fun fetchCollectCacheArticle(page: Int): Flow<MojitoResult<BeanArticleList>> {
        return flow {
            val response = mWanCache.get(
                WanCache.CacheKey.COLLECT_ARTICLE_LIST(page),
                BeanArticleList::class.java
            )
            emit(MojitoResult.Success(response))
        }.useCase()
    }

    fun fetchMineShareArticle(page: Int): Flow<MojitoResult<BeanArticleList>> {
        return flow {
            val response = mRepository.fetchArticlesByUrl(UrlManager.urlMineShareArticleList(page))
            if (response.isSuccess()) {
                if (page == 1) mWanCache.put(
                    WanCache.CacheKey.MINE_SHARE_ARTICLE_LIST(page),
                    response.data
                )
                emit(response.toSuccessResult())
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }

    fun fetchMineShareCacheArticle(page: Int): Flow<MojitoResult<BeanArticleList>> {
        return flow {
            val response = mWanCache.get(
                WanCache.CacheKey.MINE_SHARE_ARTICLE_LIST(page),
                BeanArticleList::class.java
            )
            emit(MojitoResult.Success(response))
        }.useCase()
    }

    fun fetchProjectArticle(page: Int, cid: Int): Flow<MojitoResult<BeanArticleList>> {
        return flow {
            val response =
                mRepository.fetchArticlesByUrl(UrlManager.urlProjectArticleList(page, cid))
            if (response.isSuccess()) {
                if (page == 1) mWanCache.put(
                    WanCache.CacheKey.PROJECT_ARTICLE_LIST(cid, page),
                    response.data
                )
                emit(response.toSuccessResult())
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }

    fun fetchProjectCacheArticle(page: Int, cid: Int): Flow<MojitoResult<BeanArticleList>> {
        return flow {
            val response = mWanCache.get(
                WanCache.CacheKey.PROJECT_ARTICLE_LIST(cid, page),
                BeanArticleList::class.java
            )
            emit(MojitoResult.Success(response))
        }.useCase()
    }

    fun fetchWeChatArticle(page: Int, cid: Int): Flow<MojitoResult<BeanArticleList>> {
        return flow {
            val response =
                mRepository.fetchArticlesByUrl(UrlManager.urlProjectArticleList(page, cid))
            if (response.isSuccess()) {
                if (page == 1) mWanCache.put(
                    WanCache.CacheKey.WXARTICLE_LIST(cid, page),
                    response.data
                )
                emit(response.toSuccessResult())
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }

    fun fetchWeChatCacheArticle(page: Int, cid: Int): Flow<MojitoResult<BeanArticleList>> {
        return flow {
            val response = mWanCache.get(
                WanCache.CacheKey.WXARTICLE_LIST(cid, page),
                BeanArticleList::class.java
            )
            emit(MojitoResult.Success(response))
        }.useCase()
    }

    fun fetchShareArticle(page: Int): Flow<MojitoResult<BeanArticleList>> {
        return flow {
            val response = mRepository.fetchArticlesByUrl(UrlManager.urlShareArticleList(page))
            if (response.isSuccess()) {
                if (page == 0) mWanCache.put(
                    WanCache.CacheKey.USER_ARTICLE_LIST(page),
                    response.data
                )
                emit(response.toSuccessResult())
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }

    fun fetchShareCacheArticle(page: Int): Flow<MojitoResult<BeanArticleList>> {
        return flow {
            val response = mWanCache.get(
                WanCache.CacheKey.USER_ARTICLE_LIST(page),
                BeanArticleList::class.java
            )
            emit(MojitoResult.Success(response))
        }.useCase()
    }

    fun fetchSearchArticle(page: Int, keyword: String): Flow<MojitoResult<BeanArticleList>> {
        return flow {
            val response = mRepository.search(UrlManager.urlSearchArticleList(page), keyword)
            if (response.isSuccess()) {
                if (page == 0) mWanCache.put(WanCache.CacheKey.SEARCH(keyword, page), response.data)
                emit(response.toSuccessResult())
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }

//    fun fetchSearchCacheArticle(page: Int, keyword: String): Flow<MojitoResult<BeanArticleList>> {
//        return flow {
//            val response = mWanCache.get(
//                WanCache.CacheKey.SEARCH(keyword, page),
//                BeanArticleList::class.java
//            )
//            emit(MojitoResult.Success(response))
//        }.useCase()
//    }

    fun fetchSystemArticle(page: Int, cid: Int): Flow<MojitoResult<BeanArticleList>> {
        return flow {
            val response =
                mRepository.fetchArticlesByUrl(UrlManager.urlSystemArticleList(page, cid))
            if (response.isSuccess()) {
                if (page == 0) mWanCache.put(
                    WanCache.CacheKey.KNOWLEDGE_ARTICLE_LIST(cid, page),
                    response.data
                )
                emit(response.toSuccessResult())
            } else {
                emit(response.toErrorResult())
            }
        }.useCase()
    }

    fun fetchSystemCacheArticle(page: Int, cid: Int): Flow<MojitoResult<BeanArticleList>> {
        return flow {
            val response = mWanCache.get(
                WanCache.CacheKey.KNOWLEDGE_ARTICLE_LIST(cid, page),
                BeanArticleList::class.java
            )
            emit(MojitoResult.Success(response))
        }.useCase()
    }

}