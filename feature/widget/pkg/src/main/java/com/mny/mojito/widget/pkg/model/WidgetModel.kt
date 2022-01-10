package com.mny.mojito.widget.pkg.model

import android.graphics.Color
import com.mny.mojito.widget.pkg.R

/**
 * WidgetModel
 * Desc:
 */
class WidgetModel(val name: String, val mainColor: Int = 0, val img: Int = 0) {
    companion object {
        val launcher = WidgetModel("启动中心", Color.BLACK, R.drawable.ic_launcher)
        val live = WidgetModel("生辰", Color.BLUE, R.drawable.ic_launcher)
        val motto = WidgetModel("座右铭", Color.WHITE, R.drawable.widget_ic_motto)
        val timer = WidgetModel("时光便签", Color.WHITE, R.drawable.widget_ic_timer_note)
        val today = WidgetModel("今日", Color.WHITE, R.drawable.widget_ic_today)

        val widgets = listOf(motto, timer, today)
    }
}