package com.mojito.common.helper

import com.mny.mojito.error.BaseError
import com.tencent.bugly.crashreport.BuglyLog
import com.tencent.bugly.crashreport.CrashReport

/**
 * BuglyHelper
 * Desc:
 */


class BuglyReportError(code: Int = DEFAULT_ERROR_CODE, msg: String = "", cause: Throwable? = null) :
    BaseError(code, msg, cause)

object BuglyHelper {
    fun init() {
        // 内存缓存设置为 15K，最大为 30K
        BuglyLog.setCache(15 * 1024)
    }

    fun reportError(cause: Throwable) {
        CrashReport.postCatchedException(BuglyReportError(cause = cause))
    }

    fun reportError(err: BuglyReportError) {
        CrashReport.postCatchedException(err)
    }

    fun reportError(errMsg: String, cause: Throwable) {
        CrashReport.postCatchedException(BuglyReportError(msg = errMsg, cause = cause))
    }

    fun d(msg: String, tag: String = "BuglyHelper") {
        BuglyLog.d(tag, msg)
    }
}