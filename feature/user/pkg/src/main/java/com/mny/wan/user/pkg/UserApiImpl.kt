package com.mny.wan.user.pkg

import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ApiUtils
import com.mny.wan.user.export.UserApi
import com.mny.wan.user.pkg.helper.UserRouterHelper
import com.mny.wan.user.pkg.presentation.mine.MineFragment

@ApiUtils.Api
class UserApiImpl : UserApi() {
    override fun getMineFragment(): Fragment = MineFragment.newInstance()

    override fun goLogin() {
        UserRouterHelper.goLogin()
    }
}