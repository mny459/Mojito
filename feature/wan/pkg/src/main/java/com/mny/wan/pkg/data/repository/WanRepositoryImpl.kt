package com.mny.wan.pkg.data.repository

import com.mny.wan.pkg.base.BaseRepository
import com.mny.wan.pkg.data.remote.model.*
import com.mny.wan.pkg.data.remote.service.WanService
import com.mny.wan.pkg.domain.repository.WanRepository
import com.mojito.common.data.remote.model.*
import javax.inject.Inject

class WanRepositoryImpl @Inject constructor(private val mWanService: WanService) : BaseRepository(),
    WanRepository {

    override suspend fun fetchArticlesByUrl(url: String): BaseResponse<BeanArticleList> {
        return mWanService.fetchArticleList(url)
    }

    override suspend fun fetchTopArticles(): BaseResponse<List<BeanArticle>> {
        return mWanService.fetchArticleTopList()
    }

    override suspend fun fetchProjectTree(): BaseResponse<List<BeanProject>> {
        return mWanService.fetchProjectTree()
    }

    override suspend fun fetchWeChatTree(): BaseResponse<List<BeanSystemParent>> {
        return mWanService.fetchWeChatTree()
    }

    override suspend fun fetchSystemTree(): BaseResponse<List<BeanSystemParent>> {
        return mWanService.fetchSystemTree()
    }

    override suspend fun fetchNavTree(): BaseResponse<List<BeanNav>> {
        return mWanService.fetchNavTree()
    }

    override suspend fun fetchHotKey(): BaseResponse<List<BeanHotKey>> {
        return mWanService.hotKey()
    }

    override suspend fun search(url: String, keyword: String): BaseResponse<BeanArticleList> {
        return mWanService.search(url, keyword)
    }

    override suspend fun fetchBannerList(): BaseResponse<MutableList<BeanBanner>> {
        return mWanService.fetchBannerList()
    }

    override suspend fun collectArticle(id: Int): BaseNullDataResp {
        return mWanService.collectWanArticle(id)
    }

    override suspend fun cancelCollectArticle(id: Int): BaseNullDataResp {
        return mWanService.cancelCollectWanArticle(id)
    }

}