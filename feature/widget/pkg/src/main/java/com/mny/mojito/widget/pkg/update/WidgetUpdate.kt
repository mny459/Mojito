package com.mny.mojito.widget.pkg.update

import android.appwidget.AppWidgetManager
import android.content.Context

/**
 * WidgetUpdator
 * @author caicai
 * Created on 2021-11-08 14:08
 * Desc:
 */
abstract class WidgetUpdate {
    private val mWidgetIdList = hashSetOf<Int>()
    abstract val action: String

    fun addId(id: Int) {
        mWidgetIdList.add(id)
    }

    fun removeId(id: Int) {
        mWidgetIdList.remove(id)
    }

    fun updateAppWidget(context: Context) {
        mWidgetIdList.forEach {
            updateAppWidget(context, AppWidgetManager.getInstance(context), it)
        }
    }

    fun updateAppWidget(context: Context, appWidgetId: Int) {
        updateAppWidget(context, AppWidgetManager.getInstance(context), appWidgetId)
    }

    open fun updateAppWidget(
        context: Context,
        widgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        addId(appWidgetId)
    }
}