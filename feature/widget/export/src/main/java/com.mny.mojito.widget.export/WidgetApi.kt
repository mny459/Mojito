package com.mny.mojito.widget.export

import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ApiUtils

/**
 * EyepetizerApi
 * Desc:
 */
abstract class WidgetApi : ApiUtils.BaseApi() {
    abstract fun goWidget()
}