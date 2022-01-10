package com.mojito.common.app.tasks

import com.effective.android.anchors.task.Task
import com.mny.mojito.entension.dp
import com.mojito.common.R
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import javax.inject.Inject

/**
 * UserTask
 * Desc:
 */
class RefreshTask @Inject constructor() : Task(LaunchTaskName.TASK_REFRESH, false) {
    override fun run(name: String) {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            layout.apply {
                setDisableContentWhenLoading(true)
            }
            ClassicsHeader(context)
        }
        // 设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            // 指定为经典Footer，默认是 BallPulseFooter
            layout.apply {
                setEnableFooterFollowWhenNoMoreData(true)
                setEnableLoadMoreWhenContentNotFull(false)
                setDisableContentWhenLoading(true)
                setFooterHeight(56.dp().toFloat())
            }
            ClassicsFooter(context).apply {
                setDrawableSize(20f)
            }
        }
    }

}