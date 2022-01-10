package com.mny.mojito.widget.pkg.helper

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.mny.mojito.widget.pkg.presentation.today.TodayWidgetProvider
import java.util.*

/**
 * AlarmManagerHelper
 * @author caicai
 * Created on 2021-11-08 14:00
 * Desc:
 */
object AlarmManagerHelper {

    fun startAlarmManager(context: Context, targetAppProvider: Class<*>, action: String) {
        val nowMills = TimeUtils.getNowMills()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = nowMills
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val tomorrow = "$year-$month-$day 00:00:00"
        val tomorrowInMills = TimeUtils.string2Millis(tomorrow)
        LogUtils.d("startAlarmManager tomorrow = $tomorrow firstTime = $tomorrowInMills")
        val manager =
            context.applicationContext.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(context, targetAppProvider)
        intent.action = action
        val broadcast = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            0
        )
        manager?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            tomorrowInMills,
            24 * 60 * 60 * 1000L,
            broadcast
        )
    }

    fun cancelAlarmManager(context: Context, targetAppProvider: Class<*>, action: String) {
        val manager =
            context.applicationContext.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(context, targetAppProvider)
        intent.action = action
        val broadcast = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            0
        )
        LogUtils.d("cancelAlarmManager")
        manager?.cancel(broadcast)
    }
}