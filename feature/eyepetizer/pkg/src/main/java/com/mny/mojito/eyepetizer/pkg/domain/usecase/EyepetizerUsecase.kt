package com.mny.mojito.eyepetizer.pkg.domain.usecase

import com.mny.mojito.eyepetizer.pkg.data.remote.model.EyepetizerDialy
import com.mny.mojito.eyepetizer.pkg.domain.repository.EyepetizerRepository
import com.mny.mojito.http.MojitoResult
import com.mny.mojito.entension.useCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * EyepetizerUsecase
 * Desc:
 */
@Singleton
class EyepetizerUsecase @Inject constructor(private val mRepository: EyepetizerRepository) {

    fun fetchUrl(url: String): Flow<MojitoResult<EyepetizerDialy>> {
        return flow {
            val result = mRepository.fetchDaily(url)
            emit(MojitoResult.Success(result))
        }.useCase()
    }

}