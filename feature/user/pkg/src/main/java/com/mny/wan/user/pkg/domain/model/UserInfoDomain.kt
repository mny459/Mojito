package com.mny.wan.user.pkg.domain.model

data class UserInfoDomain(
    val isLogin: Boolean = false,
    val nickname: String = "",
    val rank: Int = 0,
    val level: Int = 0,
)
