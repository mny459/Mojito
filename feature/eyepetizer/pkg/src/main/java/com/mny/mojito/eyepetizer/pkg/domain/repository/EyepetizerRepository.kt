package com.mny.mojito.eyepetizer.pkg.domain.repository

import com.mny.mojito.eyepetizer.pkg.data.remote.model.EyepetizerDialy

interface EyepetizerRepository {
    suspend fun fetchDaily(url: String): EyepetizerDialy
}