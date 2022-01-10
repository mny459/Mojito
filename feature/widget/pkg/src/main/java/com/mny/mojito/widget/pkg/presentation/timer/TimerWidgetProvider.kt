package com.mny.mojito.widget.pkg.presentation.timer

import android.appwidget.AppWidgetProvider
import com.mny.mojito.widget.pkg.app.BaseAppWidgetProvider
import com.mny.mojito.widget.pkg.update.TimerNoteWidgetUpdate
import com.mny.mojito.widget.pkg.update.WidgetUpdate

/**
 * [AppWidgetProvider]
 */
class TimerWidgetProvider : BaseAppWidgetProvider() {
    override val update: WidgetUpdate
        get() = TimerNoteWidgetUpdate
    override val curClass: Class<*>
        get() = this.javaClass
}