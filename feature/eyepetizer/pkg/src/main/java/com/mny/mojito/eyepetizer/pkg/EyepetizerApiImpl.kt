package com.mny.mojito.eyepetizer.pkg

import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ApiUtils
import com.mny.mojito.eyepetizer.export.EyepetizerApi
import com.mny.mojito.eyepetizer.pkg.presentation.EyepetizerDailyFragment

@ApiUtils.Api
class EyepetizerApiImpl : EyepetizerApi() {
    override fun getEyepetizerFragment(): Fragment = EyepetizerDailyFragment.newInstance()
}