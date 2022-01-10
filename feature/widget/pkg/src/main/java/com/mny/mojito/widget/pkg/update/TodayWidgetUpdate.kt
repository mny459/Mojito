package com.mny.mojito.widget.pkg.update

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.blankj.utilcode.util.TimeUtils
import com.mny.mojito.widget.pkg.BuildConfig
import com.mny.mojito.widget.pkg.R
import com.mny.mojito.widget.pkg.data.local.WidgetHelper
import com.mny.mojito.widget.pkg.di.WidgetDaoEntryPoint
import com.mny.mojito.widget.pkg.ktx.curLocale
import com.mny.mojito.widget.pkg.model.a
import com.mny.mojito.widget.pkg.model.q
import com.mny.mojito.widget.pkg.presentation.today.TodayConfigurePickerActivity
import com.mny.mojito.widget.pkg.presentation.today.TodayPickerActivity
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

/**
 * TodayWidgetUpdate
 * @author caicai
 * Created on 2021-11-08 14:13
 * Desc:
 */
object TodayWidgetUpdate : WidgetUpdate() {

    override val action: String
        get() = "com.mny.mojito.widget.TODAY_WIDGET_UPDATE"

    override fun updateAppWidget(
        context: Context,
        widgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        super.updateAppWidget(context, widgetManager, appWidgetId)

        val entryPoint =
            EntryPointAccessors.fromApplication(context, WidgetDaoEntryPoint::class.java)
        val dao = entryPoint.todayDao()
        val coroutineScope = entryPoint.getCoroutineScope()

        val pi =
            Intent(context, TodayPickerActivity::class.java).let { intent ->
                intent.putExtra("appId", appWidgetId)
                PendingIntent.getActivity(
                    context,
                    appWidgetId,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }

        val view = RemoteViews(context.packageName, R.layout.widget_today)
        view.setOnClickPendingIntent(R.id.rl_container, pi)

        coroutineScope.launch {
            val id = WidgetHelper.getTimerNoteByWidgetId(appWidgetId)
            var data = dao.fetchTodayById(id)
            if (data == null) {
                val newestData = dao.fetchNewestToday()
                data = if (newestData.isEmpty()) null else newestData[0]
            }
            withContext(Dispatchers.Main) {
                // 刷新
                val curLocale = curLocale()
                val format = if (BuildConfig.DEBUG) SimpleDateFormat(
                    "yyyy-MM-dd hh:mm:ss",
                    curLocale
                )
                else SimpleDateFormat("yyyy-MM-dd", curLocale)
                view.setTextViewText(R.id.tv_date, TimeUtils.getNowString(format))
                view.setTextViewText(R.id.tv_friday_q, data?.q)
                view.setTextViewText(R.id.tv_friday_a, data?.a)
                widgetManager.updateAppWidget(appWidgetId, view)
            }
        }
    }
}