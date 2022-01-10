package com.mojito.common.webview

import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.mojito.common.databinding.WebViewFragmentBinding
import com.mojito.common.helper.HostInterceptHelper
import com.mojito.common.helper.SettingHelper
import com.mojito.common.helper.isNightMode
import com.tencent.smtt.export.external.interfaces.IX5WebSettings
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 原生WebView的使用
 */
@AndroidEntryPoint
class WebViewFragment : BaseWebViewFragment<WebViewFragmentBinding>() {

    // 当前标题
    private var mWebView: WebView? = null
    private var mIsAppDarkMode = false

    @Inject
    lateinit var mSettingHelper: SettingHelper
    override fun initWebView(view: View) {
        mIsAppDarkMode =
            mSettingHelper.isThemeDark() || (mSettingHelper.isThemeFollowSystem() && requireActivity().isNightMode())
        val layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        // 为了避免内存泄漏，采用 new 的方式来构建 WebView
        val webView = WebView(requireContext())
        webView.layoutParams = layoutParams
        // 背景透明
        webView.setBackgroundResource(android.R.color.transparent)
        webView.setBackgroundColor(0)
        webView.background.alpha = 0
        webView.overScrollMode = View.OVER_SCROLL_NEVER
        mBinding.flWebViewContainer.addView(webView)
        mWebView = webView
    }

    override fun initClient() {
        mWebView?.webViewClient = object : WebViewClient() {
            /**
             * 拦截页面加载
             * true: 由宿主处理该 url
             * false：默认由 WebView 处理
             */
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean = shouldOverrideUrlLoading(request.url)

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean =
                shouldOverrideUrlLoading(Uri.parse(url))

            /**
             * 如果是白名单中的 url 就返回 false，交由 WebView 默认处理
             */
            private fun shouldOverrideUrlLoading(uri: Uri): Boolean {
                LogUtils.v("WebView shouldOverrideUrlLoading $uri")
                return !HostInterceptHelper.isWhiteHost(uri.host)
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                LogUtils.v("onPageFinished $url")
            }

            /**
             * 拦截加载的请求
             * 不在主线程中，所以不要在这里做UI操作
             */
            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                val reqUri = request?.url
                if (reqUri != null) {
                    syncCookiesForWanAndroid(reqUri.toString())
                }
                return super.shouldInterceptRequest(view, request)
            }

            override fun shouldInterceptRequest(
                view: WebView?,
                url: String?
            ): WebResourceResponse? {
                if (url != null && url.isNotEmpty()) {
                    val reqUri = Uri.parse(url)
                    syncCookiesForWanAndroid(reqUri.toString())
                }
                return super.shouldInterceptRequest(view, url)
            }

            // 处理 https
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
//                super.onReceivedSslError(view, handler, error)
                LogUtils.e("onReceivedSslError - $error")
                handler?.proceed()
            }

            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                LogUtils.e("errorOnLoadWebUrl errorCode = $errorCode, description = $description failingUrl = $failingUrl")
            }
        }
        // 最常用的就是更新 进度 和 标题
        mWebView?.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                // TODO 更新进度
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                title?.apply {
                    mViewModel.updateTitle(this.trim())
                }
            }
        }
    }

    override fun initSettings() {
        mWebView?.settings?.apply {
            // 设置支持 JS
            javaScriptEnabled = true
            // 支持通过 JS 打开新窗口
            javaScriptCanOpenWindowsAutomatically = false
            // 设置可以访问文件
            allowFileAccess = true
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS

            // 缩放操作
            // 1. 设置支持缩放，默认为true。是下面那个的前提。
            setSupportZoom(false)
            // 设置内置的缩放控件。若为false，则该WebView不可缩放
            builtInZoomControls = false
            // 隐藏原生的缩放控件
            displayZoomControls = true

            // 设置自适应屏幕，两者合用
            // 1. 将图片调整到适合 WebView 的大小
            useWideViewPort = true
            // 2. 缩放至屏幕的大小
            loadWithOverviewMode = true

            setAppCacheEnabled(true)
            domStorageEnabled = true
            setGeolocationEnabled(true)
            // 设置 WebView 中的缓存策略
            // LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
            // LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
            // LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
            // LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
            cacheMode = if (NetworkUtils.isConnected()) WebSettings.LOAD_DEFAULT
            else WebSettings.LOAD_CACHE_ELSE_NETWORK

            // 特别注意：5.1以上默认禁止了https和http混用，以下方式是开启
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }

            //支持自动加载图片
            loadsImagesAutomatically = true

            //设置编码格式
            defaultTextEncodingName = "utf-8"
        }

        mWebView?.apply {
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                if (mIsAppDarkMode) {
                    WebSettingsCompat.setForceDark(settings, WebSettingsCompat.FORCE_DARK_ON)
                } else {
                    WebSettingsCompat.setForceDark(settings, WebSettingsCompat.FORCE_DARK_OFF)
                }
            }
        }
    }

    override fun pauseWebView() {
        mWebView?.onPause()
    }

    override fun resumeWebView() {
        mWebView?.onResume()
    }

    override fun loadContent(url: String) {
        // 参数说明：
        // 参数1：内容路径
        // 内容里不能出现 ’#’, ‘%’, ‘\’ , ‘?’ 这四个字符，若出现了需用 %23, %25, %27, %3f 对应来替代，否则会出现异常
        // 参数2：展示内容的类型
        // 参数3：字节码
        LogUtils.v("loadContent $url")
        mWebView?.loadUrl(url)
    }

    override fun destroyWebView() {
        mWebView?.apply {
            (parent as ViewGroup).removeView(this)
            loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            stopLoading()
            webChromeClient = null
            // webViewClient = null
            clearHistory()
            destroy()
        }
        mWebView = null
    }

    override fun back(): Boolean {
        val canGoBack = mWebView?.canGoBack() ?: false
        if (canGoBack) mWebView?.goBack()
        return canGoBack
    }
}
