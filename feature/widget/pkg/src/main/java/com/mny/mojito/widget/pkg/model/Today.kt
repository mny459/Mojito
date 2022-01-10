package com.mny.mojito.widget.pkg.model

import androidx.room.*
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.github.gzuliyujiang.wheelview.contract.TextProvider
import com.mny.mojito.widget.pkg.helper.TodayHelper
import com.mny.mojito.widget.pkg.ktx.chineseWeekDay
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * Today
 * @author caicai
 * Created on 2021-11-05 14:56
 * Desc:
 */
@Entity(tableName = "today")
@TypeConverters(TodayConvert::class)
data class Today(
    @ColumnInfo(defaultValue = "")
    var label: String = "", // 今天是[周五]吗
    var targetTime: Long = 0L,
    var answerMode: AnswerMode = AnswerMode.EVERY_WEEK,
    var createTime: Long = 0,
    var updateTime: Long = 0,
    @Ignore
    val modCount: AtomicInteger = AtomicInteger()
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    fun update() {
        modCount.incrementAndGet()
        LogUtils.d(this)
    }

    override fun toString(): String {
        return "Today(q='$q', a='$a', answerMode='$answerMode', targetTime=$targetTime, formatTargetTime='$formatTargetTime')"
    }


    companion object {
        fun createDefaultToday() = Today(
            label = "周五",
            targetTime = Calendar.FRIDAY.toLong(),
            createTime = TimeUtils.getNowMills(),
            updateTime = TimeUtils.getNowMills()
        )
    }
}

enum class AnswerMode(val mode: Int) {
    // EVERY_DAY(0), // 每天，对应 几点几分几秒
    EVERY_WEEK(1), // 每周，对应 周一到周日
    EVERY_MONTH(2), // 每月，对应 1~31 日
    EVERY_YEAR(3);// 每年，对应几月几号;

    override fun toString(): String {
        val curMode = values().firstOrNull { it.mode == mode }
        return when (curMode) {
            // EVERY_DAY -> "每天"
            EVERY_WEEK -> "每周" // 1~7
            EVERY_MONTH -> "每月" // 1~31号
            EVERY_YEAR -> "每年" // 月~日
            else -> "每周"
        }
    }
}

class TodayConvert {
    @TypeConverter
    fun toMode(mode: Int) = AnswerMode.values().first { it.mode == mode }

    @TypeConverter
    fun fromMode(mode: AnswerMode) = mode.mode
}

val Today.now: String
    get() {
        return TimeUtils.getNowString()
    }

val Today.q: String
    get() {
        return "今天是${label}吗"
    }

val Today.a: String
    get() {
        val offsetDay = when (answerMode) {
            AnswerMode.EVERY_WEEK -> TodayHelper.weekDayOffset(targetTime.toInt())
            AnswerMode.EVERY_MONTH -> TodayHelper.monthDayOffset(targetTime.toInt())
            AnswerMode.EVERY_YEAR -> TodayHelper.yearDayOffset(targetTime)
        }
        return if (offsetDay == 0) "今天是${label}"
        else "不是，还有${offsetDay}天"
    }

val Today.formatTargetTime: String
    get() {
        val format = when (answerMode) {
            AnswerMode.EVERY_WEEK -> "每周${targetTime.toInt().chineseWeekDay}"
            AnswerMode.EVERY_MONTH -> "每月${targetTime}号"
            AnswerMode.EVERY_YEAR -> {
                val date = TimeUtils.millis2Date(targetTime)
                "每年${date.month + 1}月${date.date}日"
            }
        }
        return format
    }

val answerModeOptions = arrayListOf(
    AnswerModeOption(AnswerMode.EVERY_WEEK),
    AnswerModeOption(AnswerMode.EVERY_MONTH),
    AnswerModeOption(AnswerMode.EVERY_YEAR),
).toList()

val weekOptions = arrayListOf(
    WeekOption(Calendar.MONDAY),
    WeekOption(Calendar.TUESDAY),
    WeekOption(Calendar.WEDNESDAY),
    WeekOption(Calendar.THURSDAY),
    WeekOption(Calendar.FRIDAY),
    WeekOption(Calendar.SATURDAY),
    WeekOption(Calendar.SUNDAY),
).toList()

val monthOptions = mutableListOf<MonthOption>().apply {
    repeat(31) {
        add(MonthOption(it + 1))
    }
}.toList()

interface TodayTargetTime {
    fun targetTime(): Long
}

class AnswerModeOption(val mode: AnswerMode) : TextProvider {
    override fun provideText(): String = mode.toString()
}

class WeekOption(private val day: Int) : TextProvider, TodayTargetTime {
    override fun provideText(): String = day.chineseWeekDay
    override fun targetTime(): Long = day.toLong()
}

class MonthOption(val day: Int) : TextProvider, TodayTargetTime {
    override fun provideText(): String = day.toString()
    override fun targetTime(): Long = day.toLong()
}