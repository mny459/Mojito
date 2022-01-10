package com.mny.wan.pkg.data.remote.service

import com.mojito.common.data.remote.model.*
import retrofit2.http.*

interface WanService {

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@Field("username") username: String, @Field("password") password: String)
            : BaseResponse<BeanUserInfo>

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") rePassword: String
    ): BaseResponse<BeanUserInfo>

    @GET("/user/logout/json")
    suspend fun logout(): BaseResponse<String>

    @GET
    suspend fun fetchArticleList(@Url path: String): BaseResponse<BeanArticleList>

    @GET("/article/top/json")
    suspend fun fetchArticleTopList(): BaseResponse<List<BeanArticle>>

    @GET("/banner/json")
    suspend fun fetchBannerList(): BaseResponse<MutableList<BeanBanner>>

    @GET("/project/tree/json")
    suspend fun fetchProjectTree(): BaseResponse<List<BeanProject>>

    @GET("/tree/json")
    suspend fun fetchSystemTree(): BaseResponse<List<BeanSystemParent>>

    @GET("/wxarticle/chapters/json")
    suspend fun fetchWeChatTree(): BaseResponse<List<BeanSystemParent>>

    @GET("/navi/json")
    suspend fun fetchNavTree(): BaseResponse<List<BeanNav>>

    @FormUrlEncoded
    @POST
    suspend fun search(
        @Url path: String, @Field("k") keyWord: String
    ): BaseResponse<BeanArticleList>

    @POST("/lg/collect/{id}/json")
    suspend fun collectWanArticle(@Path("id") id: Int): BaseNullDataResp

    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun cancelCollectWanArticle(@Path("id") id: Int): BaseNullDataResp

    @GET("/lg/coin/userinfo/json")
    suspend fun coinInfo(): BaseResponse<BeanCoin>

    @GET("/hotkey/json")
    suspend fun hotKey(): BaseResponse<List<BeanHotKey>>

    // 广场
    @GET
    suspend fun fetchMineShareArticleList(@Url path: String): BaseResponse<BeanMineShareArticle>
}