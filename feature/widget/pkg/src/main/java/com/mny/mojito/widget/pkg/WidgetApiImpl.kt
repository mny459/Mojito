package com.mny.mojito.widget.pkg

import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ApiUtils
import com.mny.mojito.widget.export.WidgetApi

/**
 * WidgetApiImpl
 * Desc:
 */
@ApiUtils.Api
class WidgetApiImpl : WidgetApi() {
    override fun goWidget() {
        ActivityUtils.startActivity(MainActivity::class.java)
    }
}