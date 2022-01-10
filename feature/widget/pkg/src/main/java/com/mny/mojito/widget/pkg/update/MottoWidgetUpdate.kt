package com.mny.mojito.widget.pkg.update

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.mny.mojito.widget.pkg.R
import com.mny.mojito.widget.pkg.data.local.WidgetHelper
import com.mny.mojito.widget.pkg.di.WidgetDaoEntryPoint
import com.mny.mojito.widget.pkg.presentation.motto.MottoPickerActivity
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
object MottoWidgetUpdate : WidgetUpdate() {

    override val action: String
        get() = "com.mny.mojito.widget.MOTTO_WIDGET_UPDATE"

    override fun updateAppWidget(
        context: Context,
        widgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        super.updateAppWidget(context, widgetManager, appWidgetId)

        val entryPoint =
            EntryPointAccessors.fromApplication(context, WidgetDaoEntryPoint::class.java)
        val dao = entryPoint.mottoDao()
        val coroutineScope = entryPoint.getCoroutineScope()

        val pi = Intent(context, MottoPickerActivity::class.java).let { intent ->
            intent.putExtra("appId", appWidgetId)
            PendingIntent.getActivity(
                context,
                appWidgetId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        val view = RemoteViews(context.packageName, R.layout.widget_motto)
        view.setOnClickPendingIntent(R.id.rl_container, pi)

        coroutineScope.launch {
            val id = WidgetHelper.getMottoByWidgetId(appWidgetId)
            var motto = dao.fetchMottoById(id)
            if (motto == null) {
                val fetchNewestMotto = dao.fetchNewestMotto()
                motto = if (fetchNewestMotto.isEmpty()) null else fetchNewestMotto[0]
            }
            withContext(Dispatchers.Main) {
                // 刷新
                val content = motto?.content ?: "您还未编辑任何座右铭"
                view.setTextViewText(R.id.tv_motto, content)
                widgetManager.updateAppWidget(appWidgetId, view)
            }
        }
    }
}