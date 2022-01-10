package com.mny.mojito.eyepetizer.pkg.data.remote

import com.mny.mojito.eyepetizer.pkg.data.remote.model.EyepetizerDialy
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * EyepetizerService
 * Desc:
 */
interface EyepetizerService {
    @GET
    suspend fun fetchDaily(@Url url: String): EyepetizerDialy
}