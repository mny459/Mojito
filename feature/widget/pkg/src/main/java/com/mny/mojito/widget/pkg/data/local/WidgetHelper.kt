package com.mny.mojito.widget.pkg.data.local

import com.mny.mojito.data.kv.KVHelper

/**
 * WidgetHelper
 * @author caicai
 * Created on 2021-09-08 14:33
 * Desc:
 */
object WidgetHelper {
    private const val MOTTO_PREFIX = "widget_motto_"
    private const val TODAY_PREFIX = "widget_today_"
    private const val TIMER_NOTE_PREFIX = "widget_timer_note_"
    fun saveMottoByWidgetId(widgetId: Int, id: Long) {
        KVHelper.put("$MOTTO_PREFIX$widgetId", id)
    }

    fun saveTodayByWidgetId(widgetId: Int, id: Long) {
        KVHelper.put("$TODAY_PREFIX$widgetId", id)
    }

    fun saveTimerNoteByWidgetId(widgetId: Int, id: Long) {
        KVHelper.put("$TIMER_NOTE_PREFIX$widgetId", id)
    }

    fun getMottoByWidgetId(widgetId: Int) = KVHelper.getLong("$MOTTO_PREFIX$widgetId", 0L)
    fun getTodayByWidgetId(widgetId: Int) = KVHelper.getLong("$TODAY_PREFIX$widgetId", 0L)
    fun getTimerNoteByWidgetId(widgetId: Int) = KVHelper.getLong("$TIMER_NOTE_PREFIX$widgetId", 0L)
}