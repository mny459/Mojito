package com.mny.mojito.eyepetizer.export

import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ApiUtils

/**
 * EyepetizerApi
 * Desc:
 */
abstract class EyepetizerApi : ApiUtils.BaseApi() {
    abstract fun getEyepetizerFragment(): Fragment
}