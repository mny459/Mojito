package com.mojito.common.app.tasks

import android.content.Context
import com.blankj.utilcode.util.ProcessUtils
import com.effective.android.anchors.task.Task
import com.mojito.common.BuildConfig
import com.mojito.common.data.local.UserHelper
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.smtt.sdk.WebView
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.LinkedHashMap
import javax.inject.Inject

class BuglyTask @Inject constructor(@ApplicationContext val context: Context) :
    Task(LaunchTaskName.TASK_BUGLY, true) {

    override fun run(name: String) {
        if (!ProcessUtils.isMainProcess()) {
            return
        }
        val userStrategy = CrashReport.UserStrategy(context)
        userStrategy.isUploadProcess = ProcessUtils.isMainProcess()
        CrashReport.setIsDevelopmentDevice(context, BuildConfig.DEBUG)
        userStrategy.setCrashHandleCallback(object : CrashReport.CrashHandleCallback() {
            override fun onCrashHandleStart(
                crashType: Int,
                errorType: String,
                errorMessage: String,
                errorStack: String
            ): MutableMap<String, String> {
                val map = LinkedHashMap<String, String>()
                val x5CrashInfo = WebView.getCrashExtraMessage(context)
                map["x5crashInfo"] = x5CrashInfo
                map["user"] = UserHelper.user?.nickname ?: ""
                return map
            }

            override fun onCrashHandleStart2GetExtraDatas(
                crashType: Int,
                errorType: String,
                errorMessage: String,
                errorStack: String
            ): ByteArray? {
                return try {
                    "Extra data.".toByteArray(charset("UTF-8"))
                } catch (e: Exception) {
                    null
                }
            }
        })
        CrashReport.initCrashReport(context, BuildConfig.APPID_BUGLY, BuildConfig.DEBUG, userStrategy)
    }

}