package com.mny.mojito.eyepetizer.pkg.data.repository

import com.mny.mojito.eyepetizer.pkg.data.remote.EyepetizerService
import com.mny.mojito.eyepetizer.pkg.data.remote.model.EyepetizerDialy
import com.mny.mojito.eyepetizer.pkg.di.EyepetizerModule
import com.mny.mojito.eyepetizer.pkg.domain.repository.EyepetizerRepository
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * EyepetizerRepositoryImpl
 * Desc:
 */
@Singleton
class EyepetizerRepositoryImpl @Inject constructor(@EyepetizerModule.EyeRetrofit val retrofit: Retrofit) :
    EyepetizerRepository {

    override suspend fun fetchDaily(url: String): EyepetizerDialy {
        return retrofit.create(EyepetizerService::class.java).fetchDaily(url)
    }
}