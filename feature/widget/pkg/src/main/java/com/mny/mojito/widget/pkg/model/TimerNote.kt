package com.mny.mojito.widget.pkg.model

import androidx.room.*
import com.blankj.utilcode.util.TimeUtils
import com.mny.mojito.widget.pkg.ktx.dataBetween
import com.mny.mojito.widget.pkg.ktx.dataBetweenNow
import java.util.concurrent.atomic.AtomicInteger

/**
 * TimerNote
 * @author caicai
 * Created on 2021-11-05 15:06
 * Desc:
 */
@Entity(tableName = "timer_note")
@TypeConverters(TimerNoteConvert::class)
data class TimerNote(
    @ColumnInfo(defaultValue = "")
    var name: String = "",
    var mode: TimerNoteMode = TimerNoteMode.TOTAL,
    var startTime: Long = 0L,
    var targetTime: Long = 0L,
    var createTime: Long = 0,
    var updateTime: Long = 0,
    @Ignore
    val modCount: AtomicInteger = AtomicInteger()
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    fun update() {
        modCount.incrementAndGet()
    }

    companion object {
        fun createDefaultNote() = TimerNote(
            name = "累计日",
            startTime = TimeUtils.getNowMills(),
            targetTime = TimeUtils.getNowMills(),
            createTime = TimeUtils.getNowMills()
        )
    }
}

enum class TimerNoteMode(val format: Int) {
    TOTAL(0),// 累计
    COUNT_DOWN(1), // 倒数
}

class TimerNoteConvert {
    @TypeConverter
    fun fromFormat(format: Int) = TimerNoteMode.values().first { it.format == format }

    @TypeConverter
    fun toFormat(mode: TimerNoteMode) = mode.format
}

val TimerNote.desc: String
    get() = if (isCountDownMode) {
        "倒数日|剩余天数"
    } else {
        val now = TimeUtils.getNowMills()
        if (now < targetTime) "累计日|剩余天数"
        else "累计日|已过天数"
    }


val TimerNote.dayCountOrProgress: String
    get() {
        val now = TimeUtils.getNowMills()
        return if (isCountDownMode) {
            if (startTime > now || startTime > targetTime) "结束"
            else "${targetTime.dataBetween(startTime)}天"
        } else "${targetTime.dataBetweenNow()}天"
    }

val TimerNote.isCountDownMode
    get() = mode == TimerNoteMode.COUNT_DOWN