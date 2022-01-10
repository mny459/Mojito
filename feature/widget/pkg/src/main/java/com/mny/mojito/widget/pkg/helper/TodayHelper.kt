package com.mny.mojito.widget.pkg.helper

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.mny.mojito.widget.pkg.ktx.dataBetween
import java.util.*

/**
 * TodayHelper
 * @author caicai
 * Created on 2021-11-06 15:38
 * Desc:
 */
object TodayHelper {
    fun weekDayOffset(targetDay: Int): Int {
        val curDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK).toChineseWeekDay
        val targetChineseWeekDay = targetDay.toChineseWeekDay
        val result = when {
            curDay == targetChineseWeekDay -> 0
            targetChineseWeekDay > curDay -> {
                // 当前是周二，目标是周五
                targetChineseWeekDay - curDay
            }
            else -> {
                // 当前是周五，目标是周二
                7 - curDay + targetChineseWeekDay
            }
        }
        // LogUtils.d("Today.TodayHelper.weekDayOffset targetChineseWeekDay = $targetChineseWeekDay curDay = $curDay offset = $result")
        return result
    }

    fun monthDayOffset(targetDay: Int): Int {
        val curMonth = Calendar.getInstance()
        val curDay = curMonth.get(Calendar.DAY_OF_MONTH)
        val result = when {
            curDay == targetDay -> 0
            targetDay > curDay -> {
                targetDay - curDay
            }
            else -> {
                val nextMonth = Calendar.getInstance().apply {
                    add(Calendar.MONTH, 1)
                }
                val nextDayInMonth =
                    "${nextMonth.get(Calendar.YEAR)}-${nextMonth.get(Calendar.MONTH) + 1}-$targetDay 00:00:00"

                val curDayInMonth =
                    "${curMonth.get(Calendar.YEAR)}-${curMonth.get(Calendar.MONTH) + 1}-$curDay 00:00:00"
                val curDayInMillis = TimeUtils.string2Date(curDayInMonth).time
                val targetDayInMillis = TimeUtils.string2Date(nextDayInMonth).time
                LogUtils.d("Today.TodayHelper.monthDayOffset 现在是 $curDayInMonth 下个月的这一天是 $nextDayInMonth")
                targetDayInMillis.dataBetween(curDayInMillis).toInt()
            }
        }
        LogUtils.d("Today.TodayHelper.monthDayOffset targetDay = $targetDay curDay = $curDay offset = $result")
        return result
    }

    fun yearDayOffset(day: Long): Int {
        val targetDayOfYear = TimeUtils.millis2Date(day)
        val targetMonth = targetDayOfYear.month + 1
        val targetDay = targetDayOfYear.date
        val now = Calendar.getInstance()
        val curDayOfCurYear =
            "${now.get(Calendar.YEAR)}-${now.get(Calendar.MONTH) + 1}-${now.get(Calendar.DAY_OF_MONTH)} 00:00:00"
        val targetDayOfCurYear = "${now.get(Calendar.YEAR)}-$targetMonth-$targetDay 00:00:00"
        val targetDayOfNextYear = "${now.get(Calendar.YEAR) + 1}-$targetMonth-$targetDay 00:00:00"

        val curDayOfThisYearInMillis = TimeUtils.string2Date(curDayOfCurYear).time
        val targetDayOfThisYearInMillis = TimeUtils.string2Date(targetDayOfCurYear).time
        val result =
            when {
                curDayOfThisYearInMillis == targetDayOfThisYearInMillis -> 0
                targetDayOfThisYearInMillis > curDayOfThisYearInMillis -> {
                    targetDayOfThisYearInMillis.dataBetween(curDayOfThisYearInMillis).toInt()
                }
                else -> {
                    val theDayOfNextYearInMillis = TimeUtils.string2Date(targetDayOfNextYear).time
                    theDayOfNextYearInMillis.dataBetween(curDayOfThisYearInMillis).toInt()
                }
            }
        LogUtils.d("Today.TodayHelper.yearDayOffset thisYearOfTheDay = $targetDayOfCurYear theDayOfNextYear = $targetDayOfNextYear offset = $result")
        return result
    }

    private val Int.toChineseWeekDay: Int
        get() {
            return when (this) {
                Calendar.SUNDAY -> 7
                Calendar.MONDAY -> 1
                Calendar.TUESDAY -> 2
                Calendar.WEDNESDAY -> 3
                Calendar.THURSDAY -> 4
                Calendar.FRIDAY -> 5
                Calendar.SATURDAY -> 6
                else -> 0
            }
        }
}