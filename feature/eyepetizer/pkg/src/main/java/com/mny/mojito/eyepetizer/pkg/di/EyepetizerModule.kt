package com.mny.mojito.eyepetizer.pkg.di

import android.os.Build
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.ScreenUtils
import com.google.gson.Gson
import com.mny.mojito.di.factory.OkHttpFactory
import com.mny.mojito.di.factory.RetrofitFactory
import com.mny.mojito.eyepetizer.pkg.data.UrlManager
import com.mny.mojito.eyepetizer.pkg.data.remote.EyepetizerService
import com.mny.mojito.eyepetizer.pkg.data.repository.EyepetizerRepositoryImpl
import com.mny.mojito.eyepetizer.pkg.domain.repository.EyepetizerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import java.util.*
import java.util.concurrent.ExecutorService
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EyepetizerModule {

    @Singleton
    @Binds
    abstract fun bindEyepetizerRepository(repository: EyepetizerRepositoryImpl): EyepetizerRepository

    @Module
    @InstallIn(SingletonComponent::class)
    object EyepetizerDataModule {

        @EyeOkHttpClient
        @Singleton
        @Provides
        fun provideOkHttpClient(
            builder: OkHttpClient.Builder,
            interceptor: Interceptor,
            executorService: ExecutorService
        ): OkHttpClient {
            builder.addInterceptor(HeaderInterceptor())
            builder.addInterceptor(BasicParamsInterceptor())
            return OkHttpFactory.createOkHttp(
                builder = builder,
                interceptor = interceptor,
                executorService = executorService
            )
        }

        @EyeRetrofit
        @Singleton
        @Provides
        fun provideRetrofit(
            builder: Retrofit.Builder,
            @EyeOkHttpClient
            client: OkHttpClient,
            gson: Gson
        ): Retrofit {
            return RetrofitFactory.createRetrofit(
                builder = builder,
                client = client,
                url = UrlManager.BASE_URL.toHttpUrl(),
                gson = gson
            )
        }

//        @Singleton
//        @Provides
//        fun provideEyeService(@EyeRetrofit retrofit: Retrofit): EyepetizerService {
//            return retrofit.create(EyepetizerService::class.java)
//        }
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class EyeOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class EyeRetrofit
}

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder().apply {
            header("model", "Android")
            header("If-Modified-Since", "${Date()}")
            header("User-Agent", System.getProperty("http.agent") ?: "unknown")
        }.build()
        return chain.proceed(request)
    }
}

class BasicParamsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url
        val url = originalHttpUrl.newBuilder().apply {
            addQueryParameter("udid", DeviceUtils.getUniqueDeviceId())
            addQueryParameter("vc", "6030012")
            addQueryParameter("vn", "6.3.01")
            addQueryParameter(
                "size",
                "${ScreenUtils.getScreenWidth()}X${ScreenUtils.getScreenHeight()}"
            )
            addQueryParameter("deviceModel", DeviceUtils.getModel())
            addQueryParameter("first_channel", DeviceUtils.getManufacturer())
            addQueryParameter("last_channel", DeviceUtils.getManufacturer())
            addQueryParameter("system_version_code", "${Build.VERSION.SDK_INT}")
        }.build()
        val request = originalRequest
            .newBuilder()
            .url(url)
            .method(originalRequest.method, originalRequest.body)
            .build()
        return chain.proceed(request)
    }
}