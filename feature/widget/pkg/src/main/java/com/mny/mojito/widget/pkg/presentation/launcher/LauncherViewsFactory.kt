package com.mny.mojito.widget.pkg.presentation.launcher

import android.appwidget.AppWidgetManager.*
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.widget.pkg.R
import com.mny.mojito.widget.pkg.model.Launcher

/**
 * LauncherViewsFactory
 * @author caicai
 * Created on 2021-09-08 18:11
 * Desc:
 */
class LauncherViewsFactory(val context: Context, val intent: Intent) :
    RemoteViewsService.RemoteViewsFactory {
    private lateinit var mLaunchers: List<Launcher>
    private var mAppWidgetId = 0
    override fun onCreate() {
        LogUtils.d("onCreate: ${Thread.currentThread().name}")
        mAppWidgetId = intent.getIntExtra(
            EXTRA_APPWIDGET_ID,
            INVALID_APPWIDGET_ID
        )
        mLaunchers = List(10) { index -> Launcher(name = "QQ", schema = "mqq://", used = true) }
    }

    override fun onDataSetChanged() {
    }

    override fun onDestroy() {
    }

    override fun getCount(): Int = mLaunchers.size

    override fun getViewAt(position: Int): RemoteViews =
        RemoteViews(context.packageName, R.layout.cell_launcher).apply {
            setTextViewText(R.id.tv_name, mLaunchers[position].name)
            val launchIntent = Intent().apply {
                action = LauncherWidgetProvider.LAUNCHER_ACTION
                data = Uri.parse(mLaunchers[position].schema)
            }
            setOnClickFillInIntent(R.id.rl_container, launchIntent);
        }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = position.toLong()

    override fun hasStableIds(): Boolean = false


}