package com.mny.wan.user.pkg.data.remote.service

import com.mojito.common.data.remote.model.*
import retrofit2.http.*

interface UserService {

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): BaseResponse<BeanUserInfo>

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") rePassword: String
    ): BaseResponse<BeanUserInfo>

    @GET("/user/logout/json")
    suspend fun logout(): BaseResponse<String>

    @GET("/lg/coin/userinfo/json")
    suspend fun coinInfo(): BaseResponse<BeanCoin>

    @GET("/lg/coin/list/{page}/json")
    suspend fun coinList(@Path("page") page: Int): BaseResponse<BaseListData<BeanCoinOpDetail>>

    @GET("/coin/rank/{page}/json")
    suspend fun rankList(@Path("page") page: Int): BaseResponse<BaseListData<BeanRanking>>
}