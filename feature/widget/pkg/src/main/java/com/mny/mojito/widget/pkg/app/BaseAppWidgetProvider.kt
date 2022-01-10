package com.mny.mojito.widget.pkg.app

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.widget.pkg.helper.AlarmManagerHelper
import com.mny.mojito.widget.pkg.update.WidgetUpdate

/**
 * BaseAppWidgetProvider
 * @author caicai
 * Created on 2021-11-08 14:19
 * Desc:
 */
abstract class BaseAppWidgetProvider : AppWidgetProvider() {
    companion object {
        const val TAG = "WidgetProvider"
    }

    abstract val update: WidgetUpdate
    abstract val curClass: Class<*>
    open val updateByDay: Boolean = true

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
        LogUtils.v("$TAG(${curClass.name}) onUpdate: Widget 更新时被调用 $appWidgetIds")
        for (appWidgetId in appWidgetIds) {
            update.updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    /**
     * 被删除时调用
     *
     * @param context
     * @param appWidgetIds
     */
    override fun onDeleted(context: Context?, appWidgetIds: IntArray) {
        LogUtils.v("$TAG(${curClass.name}) onDeleted: 被删除时调用")
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            update.removeId(Integer.valueOf(appWidgetId))
        }
        if (updateByDay && context != null) {
            AlarmManagerHelper.cancelAlarmManager(context, curClass, update.action)
        }
        super.onDeleted(context, appWidgetIds)
    }

    /**
     * 第一次被添加创建时调用
     *
     * @param context
     */
    override fun onEnabled(context: Context?) {
        LogUtils.v("$TAG(${curClass.name}) onEnabled: 第一次被添加创建时调用")
        try {
            if (updateByDay && context != null) {
                AlarmManagerHelper.startAlarmManager(context, curClass, update.action)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtils.e("$TAG(${curClass.name}) onEnabled: $e")
        }
    }

    /**
     * 最后一个Widget被删除时调用
     *
     * @param context
     */
    override fun onDisabled(context: Context?) {
        LogUtils.v("$TAG(${curClass.name}) onDisabled: 最后一个Widget被删除时调用")
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val action = intent.action
        LogUtils.v("$TAG(${curClass.name}) onReceive: $action")
        when (action) {
            update.action -> {
                update.updateAppWidget(context)
            }
            else -> {
            }
        }
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }
}