package com.mny.wan.pkg.extension

import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ApiUtils
import com.mny.mojito.widget.export.WidgetApi
import com.mojito.common.data.WanApi
import com.mny.wan.pkg.presentation.main.MainViewPagerActivity
import com.mny.wan.pkg.presentation.search.SearchActivity
import com.mojito.common.webview.KEY_URL
import com.mojito.common.webview.WebViewActivity
import com.mny.wan.user.export.UserApi

fun goMain() {
    ActivityUtils.startActivity(MainViewPagerActivity::class.java)
}

fun goLogin() {
    ApiUtils.getApi(UserApi::class.java)?.goLogin()
}

fun goSearch() {
    ActivityUtils.startActivity(SearchActivity::class.java)
}

fun goWidget() {
    ApiUtils.getApi(WidgetApi::class.java)?.goWidget()
}


fun goWeb(url: String) {
    val intent = Intent(ActivityUtils.getTopActivity(), WebViewActivity::class.java)
    val realUrl = if (url.startsWith("/blog/")) "${WanApi.API}$url"
    else url
    intent.putExtra(KEY_URL, realUrl)
    ActivityUtils.startActivity(intent)
}