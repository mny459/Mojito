package com.mny.mojito.error

/**
 * BaseError
 * Desc:
 */
abstract class BaseError(
    val code: Int = DEFAULT_ERROR_CODE,
    val msg: String = "",
    cause: Throwable? = null
) :
    Exception(msg, cause) {
    companion object {
        const val DEFAULT_ERROR_CODE = -1
    }

    override fun toString(): String {
        return "BaseError(code=$code, msg='$msg')"
    }

}

class RemoteError(code: Int = DEFAULT_ERROR_CODE, msg: String = "", cause: Throwable? = null) :
    BaseError(code, msg, cause)

class UseCaseError(code: Int = DEFAULT_ERROR_CODE, msg: String = "", cause: Throwable? = null) :
    BaseError(code, msg, cause)

object EmptyError : BaseError()

data class HttpError(val throwable: Throwable, var errorCode: Int, var errorMsg: String = "") :
    Exception(errorMsg, throwable)

data class BadResponseError(val errorCode: Int, val errorMsg: String) : Exception(errorMsg)

sealed class CommonError {
    data class BadResponseError(var errCode: Int, var errMsg: String = "") : CommonError()
    data class HttpError(var errCode: Int, var errMsg: String = "网络异常", val cause: Throwable) :
        CommonError()

    data class SqlError(var errCode: Int, var errMsg: String = "数据库异常", val cause: Throwable) :
        CommonError()

    data class UnknownError(var errCode: Int, var errMsg: String = "未知异常", val cause: Throwable) :
        CommonError()
}