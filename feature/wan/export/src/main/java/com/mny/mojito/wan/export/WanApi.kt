package com.mny.mojito.wan.export

import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ApiUtils

/**
 * WanApi
 * Desc:
 */
abstract class WanApi : ApiUtils.BaseApi() {
    abstract fun getHomeFragment(): Fragment
    abstract fun getQAFragment(): Fragment
    abstract fun getSystemFragment(): Fragment
    abstract fun goCollectArticle()
    abstract fun goProject()
    abstract fun goWeChat()
    abstract fun goShare()
}