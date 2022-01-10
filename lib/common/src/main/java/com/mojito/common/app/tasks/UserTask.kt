package com.mojito.common.app.tasks

import com.effective.android.anchors.task.Task
import com.mojito.common.data.local.UserHelper
import javax.inject.Inject

/**
 * UserTask
 * Desc:
 */
class UserTask @Inject constructor() : Task(LaunchTaskName.TASK_USER, false) {
    override fun run(name: String) {
        UserHelper.initUserInfo()
    }

}