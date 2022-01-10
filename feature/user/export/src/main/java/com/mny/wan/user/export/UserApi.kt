package com.mny.wan.user.export

import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ApiUtils

abstract class UserApi : ApiUtils.BaseApi() {
    abstract fun getMineFragment(): Fragment
    abstract fun goLogin()
}