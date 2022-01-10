package com.mojito.common.data.remote.model

import com.mny.mojito.error.UseCaseError
import com.mny.mojito.http.MojitoResult

fun <T : Any> BaseResponse<T>.toSuccessResult() = MojitoResult.Success(data)
fun <T : Any> BaseResponse<T>.toErrorResult() =
    MojitoResult.Error(UseCaseError(errorCode, errorMsg))