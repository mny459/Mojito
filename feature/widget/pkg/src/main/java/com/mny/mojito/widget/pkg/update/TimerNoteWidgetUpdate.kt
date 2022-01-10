package com.mny.mojito.widget.pkg.update

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.mny.mojito.widget.pkg.R
import com.mny.mojito.widget.pkg.data.local.WidgetHelper
import com.mny.mojito.widget.pkg.di.WidgetDaoEntryPoint
import com.mny.mojito.widget.pkg.model.dayCountOrProgress
import com.mny.mojito.widget.pkg.model.desc
import com.mny.mojito.widget.pkg.presentation.timer.TimerNoteConfigurePickerActivity
import com.mny.mojito.widget.pkg.presentation.timer.TimerNotePickerActivity
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * TodayWidgetUpdate
 * @author caicai
 * Created on 2021-11-08 14:13
 * Desc:
 */
object TimerNoteWidgetUpdate : WidgetUpdate() {

    override val action: String
        get() = "com.mny.mojito.widget.TIMER_WIDGET_UPDATE"

    override fun updateAppWidget(
        context: Context,
        widgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        super.updateAppWidget(context, widgetManager, appWidgetId)

        val entryPoint =
            EntryPointAccessors.fromApplication(context, WidgetDaoEntryPoint::class.java)
        val dao = entryPoint.timerNoteDao()
        val coroutineScope = entryPoint.getCoroutineScope()

        val pi = Intent(context, TimerNotePickerActivity::class.java).let { intent ->
            intent.putExtra("appId", appWidgetId)
            PendingIntent.getActivity(
                context,
                appWidgetId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        val view = RemoteViews(context.packageName, R.layout.widget_timer)
        view.setOnClickPendingIntent(R.id.rl_container, pi)

        coroutineScope.launch {
            val id = WidgetHelper.getTimerNoteByWidgetId(appWidgetId)
            var data = dao.fetchTimeNoteById(id)
            if (data == null) {
                val newestData = dao.fetchNewestTimeNote()
                data = if (newestData.isEmpty()) null else newestData[0]
            }
            withContext(Dispatchers.Main) {
                // 刷新
                view.setTextViewText(R.id.tv_desc, data?.desc)
                view.setTextViewText(
                    R.id.tv_day_count_or_progress,
                    data?.dayCountOrProgress
                )
                view.setTextViewText(R.id.tv_name, data?.name)
                widgetManager.updateAppWidget(appWidgetId, view)
            }
        }
    }
}