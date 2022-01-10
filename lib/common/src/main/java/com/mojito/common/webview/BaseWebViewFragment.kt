package com.mojito.common.webview

import android.net.Uri
import android.text.TextUtils
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.mny.mojito.base.BaseVBFragment
import com.mny.mojito.entension.observeWithViewLifecycle
import com.mojito.common.app.CommonConfig
import com.tencent.smtt.sdk.CookieManager
import okhttp3.Cookie
import okhttp3.HttpUrl.Companion.toHttpUrl

/**
 * BaseWebViewFragment
 * Desc:
 */
abstract class BaseWebViewFragment<VB : ViewBinding> : BaseVBFragment<VB>() {

    protected val mViewModel: WebViewViewModel by activityViewModels()

    override fun initView(view: View) {
        super.initView(view)
        initWebView(view)
        initSettings()
        initClient()
        mActivity?.onBackPressedDispatcher?.addCallback {
            if (!back()) {
                mActivity?.finish()
            }
        }
    }

    override fun initViewObserver(viewLifecycleOwner: LifecycleOwner) {
        super.initViewObserver(viewLifecycleOwner)
        observeWithViewLifecycle(mViewModel.stateLiveData) { state ->
            when (state) {
                is WebViewViewModel.ViewState.InitUrl -> {
                    if (state.url.isNotEmpty()) {
                        loadContent(state.url)
                    }
                }
                else -> {
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        pauseWebView()
    }

    override fun onResume() {
        super.onResume()
        resumeWebView()
    }

    override fun onDestroy() {
        destroyWebView()
        super.onDestroy()
    }

    abstract fun initWebView(view: View)
    abstract fun initSettings()
    abstract fun initClient()
    abstract fun pauseWebView()
    abstract fun resumeWebView()
    abstract fun loadContent(url: String)
    abstract fun destroyWebView()
    abstract fun back(): Boolean

    protected fun syncCookiesForWanAndroid(url: String) {
        if (TextUtils.isEmpty(url)) {
            return
        }
        val host = Uri.parse(url).host
        if (!TextUtils.equals(host, "www.wanandroid.com")) {
            return
        }
        val cookies: List<Cookie> = CommonConfig.wanCookieJar.loadForRequest(url.toHttpUrl())
        if (cookies.isEmpty()) {
            return
        }
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.removeSessionCookies(null)
        for (cookie in cookies) {
            cookieManager.setCookie(url, cookie.name + "=" + cookie.value)
        }
        cookieManager.flush()
    }

}