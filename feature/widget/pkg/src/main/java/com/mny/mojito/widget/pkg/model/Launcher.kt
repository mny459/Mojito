package com.mny.mojito.widget.pkg.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.concurrent.atomic.AtomicInteger

/**
 * Launcher
 * @author caicai
 * Created on 2021-09-08 18:12
 * Desc:
 */
@Entity(tableName = "launcher")
data class Launcher(
    var name: String = "",
    var schema: String = "",
    var used: Boolean = true,
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


}