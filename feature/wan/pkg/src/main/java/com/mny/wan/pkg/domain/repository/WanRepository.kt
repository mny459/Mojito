package com.mny.wan.pkg.domain.repository

import com.mojito.common.data.remote.model.*

interface WanRepository {
    suspend fun fetchArticlesByUrl(url: String): BaseResponse<BeanArticleList>
    suspend fun fetchTopArticles(): BaseResponse<List<BeanArticle>>
    suspend fun fetchProjectTree(): BaseResponse<List<BeanProject>>
    suspend fun fetchWeChatTree(): BaseResponse<List<BeanSystemParent>>
    suspend fun fetchSystemTree(): BaseResponse<List<BeanSystemParent>>
    suspend fun fetchNavTree(): BaseResponse<List<BeanNav>>
    suspend fun fetchHotKey(): BaseResponse<List<BeanHotKey>>
    suspend fun search(url: String, keyword: String): BaseResponse<BeanArticleList>
    suspend fun fetchBannerList(): BaseResponse<MutableList<BeanBanner>>
    suspend fun collectArticle(id: Int): BaseNullDataResp
    suspend fun cancelCollectArticle(id: Int): BaseNullDataResp
}