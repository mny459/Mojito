package com.mny.mojito.widget.pkg.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.concurrent.atomic.AtomicInteger

/**
 * Motto
 * Desc:
 */
@Entity(tableName = "motto")
data class Motto(
    @ColumnInfo(defaultValue = "")
    var content: String = "",
    var bgColor: String = "",
    var defaultStyle: Boolean = false,
    var style: Int = 0,
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

    override fun toString(): String {
        return "Motto(id=$id, content='$content', bgColor='$bgColor', defaultStyle=$defaultStyle, style=$style, createTime=$createTime, updateTime=$updateTime, modCount=$modCount)"
    }

}