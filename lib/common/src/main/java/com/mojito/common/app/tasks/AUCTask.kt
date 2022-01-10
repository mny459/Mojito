package com.mojito.common.app.tasks

import android.view.Gravity
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.effective.android.anchors.task.Task
import javax.inject.Inject

class AUCTask @Inject constructor() : Task(LaunchTaskName.TASK_LOG, false) {

    override fun run(name: String) {
        ToastUtils.getDefaultMaker().setGravity(Gravity.CENTER, 0, 0)
        LogUtils.getConfig().globalTag = "Wan"
        CrashUtils.init {
            LogUtils.d("$it")
        }
    }
}