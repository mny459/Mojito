package com.mojito.common.app.tasks

import com.effective.android.anchors.task.Task
import com.mojito.common.helper.SettingHelper
import javax.inject.Inject

/**
 * UserTask
 * Desc:
 */
class SettingTask @Inject constructor(private val settingHelper: SettingHelper): Task(LaunchTaskName.TASK_SETTING, false) {
    override fun run(name: String) {
        settingHelper.initTheme()
    }
}