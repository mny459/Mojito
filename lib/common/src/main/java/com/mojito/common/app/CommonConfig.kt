package com.mojito.common.app

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.base.delegate.AppLifecycle
import com.mojito.common.data.WanApi
import com.mny.mojito.di.module.GlobalModuleConfig
import com.mny.mojito.di.module.OkHttpConfiguration
import com.mny.mojito.integration.ModuleConfigAdapter
import com.mojito.common.data.local.UserHelper
import com.mny.mojito.utils.MojitoLog
import com.mojito.common.data.remote.http.SSLSocketClient
import okhttp3.*
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

class CommonConfig : ModuleConfigAdapter {

    override fun applyOptions(context: Context, builder: GlobalModuleConfig.Builder) {
        MojitoLog.d("applyOptions")
        builder.httpUrl(WanApi.API)
            .okHttpConfiguration(object : OkHttpConfiguration {
                override fun config(appContext: Context, target: OkHttpClient.Builder) {
                    target.sslSocketFactory(SSLSocketClient.sSLSocketFactory, object :
                        X509TrustManager {
                        override fun checkClientTrusted(
                            chain: Array<out X509Certificate>?,
                            authType: String?
                        ) {
                        }

                        override fun checkServerTrusted(
                            chain: Array<out X509Certificate>?,
                            authType: String?
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
                    }).cookieJar(wanCookieJar)
                }
            })
    }

    class WanCookieJar : CookieJar {
        private val cookieStore = hashMapOf<String, List<Cookie>>()

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            val list = cookieStore[url.host]
            return if (list.isNullOrEmpty()) {
                val tokenPass = UserHelper.getTokenPass()
                if (UserHelper.isLogin() && tokenPass.isNotEmpty()) {
                    val cookie = arrayListOf<Cookie>()
                    val userInfo = UserHelper.user
                    userInfo?.apply {
                        cookie.add(
                            Cookie.Builder()
                                .name("loginUserName")
                                .value("$username")
                                .domain(WanApi.DOMAIN)
                                .build()
                        )
                        cookie.add(
                            Cookie.Builder()
                                .name("token_pass")
                                .value("$tokenPass")
                                .domain(WanApi.DOMAIN)
                                .build()
                        )
                        cookie.add(
                            Cookie.Builder()
                                .name("loginUserName_wanandroid_com")
                                .value("$username")
                                .domain(WanApi.DOMAIN)
                                .build()
                        )
                        cookie.add(
                            Cookie.Builder()
                                .name("token_pass_wanandroid_com")
                                .value("$tokenPass")
                                .domain(WanApi.DOMAIN)
                                .build()
                        )
                    }

                    cookie
                } else {
                    emptyList()
                }
            } else list
        }

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            var save = true
            cookies.forEach {
                if (it.name == "token_pass") UserHelper.saveTokenPass(it.value)
                if (it.name == "JSESSIONID") save = false
            }
            if (save) {
                cookieStore[url.host] = cookies
            }
        }
    }

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecycle>) {
        lifecycles.add(CommonAppLifecycleImpl())
    }

    companion object {
        val wanCookieJar = WanCookieJar()
    }
}