package com.mny.wan.pkg.domain.usecase

import com.mny.mojito.error.UseCaseError
import com.mny.mojito.http.MojitoResult
import com.mny.wan.pkg.domain.repository.WanRepository
import com.mny.mojito.entension.useCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CollectUseCase @Inject constructor(
    private val mRepository: WanRepository
) {
    fun collect(id: Int): Flow<MojitoResult<Nothing>> {
        return flow {
            val response = mRepository.collectArticle(id)
            if (response.isSuccess()) {
                emit(MojitoResult.Success(null))
            } else {
                emit(MojitoResult.Error(UseCaseError(response.errorCode, response.errorMsg)))
            }
        }.useCase()
    }

    fun cancelCollect(id: Int): Flow<MojitoResult<Nothing>> {
        return flow {
            val response = mRepository.cancelCollectArticle(id)
            if (response.isSuccess()) {
                emit(MojitoResult.Success(null))
            } else {
                emit(MojitoResult.Error(UseCaseError(response.errorCode, response.errorMsg)))
            }
        }.useCase()
    }
}