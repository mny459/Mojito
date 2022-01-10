package com.mojito.common.app.tasks

import com.effective.android.anchors.*
import javax.inject.Inject

/**
 * AppStart
 * Desc: Anchors 的使用
 */
class AppStart @Inject constructor() {

    @Inject
    lateinit var mTaskFactory: MojitoTaskFactory

    fun start() {
        AnchorsManager.getInstance()
            .debuggable {
                // 是否打印日志信息
                BuildConfig.DEBUG
                false
            }
            .taskFactory {
                // 设置生成 Task 的工厂
                mTaskFactory
            }
            .anchors {
                // 需要在 Application#onCreate 结束前保证初始完毕
                arrayOf(
                    LaunchTaskName.TASK_USER,
                    LaunchTaskName.TASK_REFRESH,
                )
            }
//            .block("TASK_10000") {
//                // 等待功能在不设置 anchor 的场景下使用。如果设置了 anchor ，则等待任务应该是后置于 anchor 避免 uiThead 阻塞。
//                // block 场景的 task id 及 处理监听的 lambda
//                //根据业务进行  it.smash() or it.unlock()
//            }
            .graphics {
                // 生成图
                LaunchTaskName.UI_THREAD_TASK.sons(
                    LaunchTaskName.TASK_LOG.sons(
                        LaunchTaskName.TASK_KV.sons(
                            LaunchTaskName.TASK_USER,
                            LaunchTaskName.TASK_SETTING,
                            LaunchTaskName.TASK_BUGLY,
                        )
                    ),
                    LaunchTaskName.TASK_REFRESH,
                    LaunchTaskName.TASK_DO_KIT,
                )
                arrayOf(LaunchTaskName.UI_THREAD_TASK)
            }
            .startUp() // 启动任务
    }
}