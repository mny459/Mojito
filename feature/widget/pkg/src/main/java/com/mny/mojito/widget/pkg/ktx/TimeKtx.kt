package com.mny.mojito.widget.pkg.ktx

import com.blankj.utilcode.util.TimeUtils
import java.util.*
import kotlin.math.abs

/**
 * TimeKtx
 * @author caicai
 * Created on 2021-11-05 17:28
 * Desc:
 */
val Long.date: String
    get() = TimeUtils.millis2String(this, "yyyy/MM/dd")

fun Long.dataBetweenNow() = abs(this - TimeUtils.getNowMills()) / (1000 * 60 * 60 * 24)

fun Long.dataBetween(end: Long) = abs(this - end) / (1000 * 60 * 60 * 24)

val Int.chineseWeekDay: String
    get() = when (this) {
        Calendar.SUNDAY -> "星期日"
        Calendar.MONDAY -> "星期一"
        Calendar.TUESDAY -> "星期二"
        Calendar.WEDNESDAY -> "星期三"
        Calendar.THURSDAY -> "星期四"
        Calendar.FRIDAY -> "星期五"
        Calendar.SATURDAY -> "星期六"
        else -> "星期日"
    }