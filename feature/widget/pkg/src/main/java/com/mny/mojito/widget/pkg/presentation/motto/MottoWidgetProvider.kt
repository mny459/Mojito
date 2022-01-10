package com.mny.mojito.widget.pkg.presentation.motto

import android.appwidget.AppWidgetProvider
import com.mny.mojito.widget.pkg.app.BaseAppWidgetProvider
import com.mny.mojito.widget.pkg.update.MottoWidgetUpdate
import com.mny.mojito.widget.pkg.update.WidgetUpdate

/**
 * [AppWidgetProvider]
 */
class MottoWidgetProvider : BaseAppWidgetProvider() {
    override val update: WidgetUpdate
        get() = MottoWidgetUpdate
    override val curClass: Class<*>
        get() = this.javaClass
}