package com.mojito.common.app.tasks

import android.content.Context
import com.effective.android.anchors.task.Task
import com.mny.mojito.data.kv.KVHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * KVTask
 * Desc:
 */
class KVTask @Inject constructor(@ApplicationContext val context: Context) :
    Task(LaunchTaskName.TASK_KV, false) {
    override fun run(name: String) {
        KVHelper.init(context)
    }

}