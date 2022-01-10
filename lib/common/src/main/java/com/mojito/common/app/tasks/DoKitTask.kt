package com.mojito.common.app.tasks

import android.app.Application
import android.content.Context
import com.didichuxing.doraemonkit.DoKit
import com.effective.android.anchors.task.Task
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * UserTask
 * Desc:
 */
class DoKitTask @Inject constructor(@ApplicationContext val app: Context) : Task(LaunchTaskName.TASK_DO_KIT, false) {
    override fun run(name: String) {
        DoKit.Builder(app as Application)
            .build()
    }

}