package com.mny.wan.pkg.helper

import com.blankj.utilcode.util.ApiUtils
import com.mny.wan.user.export.UserApi

/**
 * UserRouterHelper
 * @author caicai
 * Created on 2021-10-23 15:50
 * Desc:
 */
object WanRouterHelper {
    fun goLogin() {
        ApiUtils.getApi(UserApi::class.java)?.goLogin()
    }
}