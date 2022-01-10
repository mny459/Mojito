package com.mny.mojito.entension

import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.error.UseCaseError
import com.mny.mojito.http.MojitoResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

fun <T : Any> Flow<MojitoResult<T>>.useCase(): Flow<MojitoResult<T>> {
    return onStart { emit(MojitoResult.Loading) }
        .catch { error ->
            LogUtils.d("useCase $error")
            emit(MojitoResult.Error(UseCaseError(cause = error.cause)))
        }
        .onCompletion {
            emit(MojitoResult.Complete)
        }
        .flowOn(Dispatchers.IO)
}