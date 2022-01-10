package com.mny.mojito.widget.pkg.presentation.launcher

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.widget.pkg.R

/**
 * [AppWidgetProvider]
 */
class LauncherWidgetProvider : AppWidgetProvider() {

    companion object {
        const val TAG = "Launcher"
        private const val WIDGET_UPDATE_ACTION = "com.mny.mojito.widget.LAUNCHER_WIDGET_UPDATE"
        const val LAUNCHER_ACTION = "com.mny.mojito.widget.LAUNCHER_START"
        private val sIdsSet = hashSetOf<Int>()
        fun updateAppWidget(context: Context) {
            sIdsSet.forEach {
                updateAppWidget(
                    context,
                    AppWidgetManager.getInstance(context),
                    it
                )
            }
        }

        fun updateAppWidget(context: Context, appWidgetId: Int) {
            updateAppWidget(
                context,
                AppWidgetManager.getInstance(context),
                appWidgetId
            )
        }

        fun updateAppWidget(context: Context, widgetManager: AppWidgetManager, appWidgetId: Int) {
            sIdsSet.add(appWidgetId)
            try {
                val view = RemoteViews(context.packageName, R.layout.widget_launcher)
                view.setRemoteAdapter(
                    R.id.gv_launchers,
                    Intent(context, LauncherViewService::class.java).apply {
                        putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                    }
                )
                view.setEmptyView(R.id.gv_launchers, android.R.id.empty);
                // 刷新
                val pi = Intent(context, LauncherWidgetProvider::class.java).let { intent ->
                    intent.action = LAUNCHER_ACTION
                    PendingIntent.getBroadcast(
                        context,
                        appWidgetId,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }
                view.setPendingIntentTemplate(R.id.gv_launchers, pi)
                widgetManager.updateAppWidget(appWidgetId, view)
            } catch (e: Exception) {
                LogUtils.d("$TAG updateAppWidget error $e")
                e.printStackTrace()
            }
        }
    }

    /**
     * Widget 更新时被调用
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        LogUtils.v("$TAG onUpdate: Widget 更新时被调用 $appWidgetIds")
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            sIdsSet.add(appWidgetId)
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    /**
     * 被删除时调用
     *
     * @param context
     * @param appWidgetIds
     */
    override fun onDeleted(context: Context?, appWidgetIds: IntArray) {
        LogUtils.v("$TAG onDeleted: 被删除时调用")
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            sIdsSet.remove(Integer.valueOf(appWidgetId))
        }
        super.onDeleted(context, appWidgetIds)
//        context?.stopService(Intent(context, TodayService::class.java))
    }

    /**
     * 第一次被添加创建时调用
     *
     * @param context
     */
    override fun onEnabled(context: Context?) {
        LogUtils.v("$TAG onEnabled: 第一次被添加创建时调用")
    }

    /**
     * 最后一个Widget被删除时调用
     *
     * @param context
     */
    override fun onDisabled(context: Context?) {
        LogUtils.v("$TAG onDisabled: 最后一个Widget被删除时调用")
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val action = intent.action
        LogUtils.v("$TAG onReceive: $action")
        when (action) {
            WIDGET_UPDATE_ACTION -> {
                updateAppWidget(context)
            }
            LAUNCHER_ACTION -> {
                intent.data?.let { uri ->
                    try {
                        context.startActivity(Intent().apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            intent.action = Intent.ACTION_VIEW
                            data = uri
                        })
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            else -> {
            }
        }
    }

}