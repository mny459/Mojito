package com.mojito.common.app.tasks

import com.effective.android.anchors.task.EmptyTask
import com.effective.android.anchors.task.Task
import com.effective.android.anchors.task.TaskCreator
import com.effective.android.anchors.task.project.Project
import javax.inject.Inject

/**
 * MojitoTaskCreator
 * Desc:
 */
class MojitoTaskCreator @Inject constructor() : TaskCreator {
    @Inject
    lateinit var mAUCTask: AUCTask

    @Inject
    lateinit var mDoKitTask: DoKitTask

    @Inject
    lateinit var mRefreshTask: RefreshTask

    @Inject
    lateinit var mKVTask: KVTask

    @Inject
    lateinit var mUserTask: UserTask

    @Inject
    lateinit var mSettingTask: SettingTask

    @Inject
    lateinit var mBuglyTask: BuglyTask

    override fun createTask(taskName: String): Task {
        return when (taskName) {
            LaunchTaskName.TASK_LOG -> mAUCTask
            LaunchTaskName.TASK_DO_KIT -> mDoKitTask
            LaunchTaskName.TASK_REFRESH -> mRefreshTask
            LaunchTaskName.TASK_KV -> mKVTask
            LaunchTaskName.TASK_USER -> mUserTask
            LaunchTaskName.TASK_SETTING -> mSettingTask
            LaunchTaskName.TASK_BUGLY -> mBuglyTask
            else -> EmptyTask
        }
    }
}

/**
 * Project 工厂
 */

class MojitoTaskFactory @Inject constructor(taskCreator: MojitoTaskCreator) :
    Project.TaskFactory(taskCreator)