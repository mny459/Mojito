package com.mny.mojito.widget.pkg.presentation.today

import android.appwidget.AppWidgetProvider
import com.mny.mojito.widget.pkg.app.BaseAppWidgetProvider
import com.mny.mojito.widget.pkg.update.TodayWidgetUpdate
import com.mny.mojito.widget.pkg.update.WidgetUpdate

/**
 * [AppWidgetProvider]
 */
class TodayWidgetProvider : BaseAppWidgetProvider() {

    override val update: WidgetUpdate
        get() = TodayWidgetUpdate
    override val curClass: Class<*>
        get() = this.javaClass
}