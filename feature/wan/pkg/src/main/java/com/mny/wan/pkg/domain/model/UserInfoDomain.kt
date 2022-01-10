package com.mny.wan.pkg.domain.model


/**
 * UserInfoDomain
 * Desc:
 */
data class UserInfoDomain(
    val isLogin: Boolean = false,
    val nickname: String = "",
    val rank: Int = 0,
    val level: Int = 0,
)
