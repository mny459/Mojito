package com.mny.mojito.widget.pkg.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.blankj.utilcode.util.Utils
import com.mny.mojito.widget.pkg.model.Launcher
import com.mny.mojito.widget.pkg.model.Motto
import com.mny.mojito.widget.pkg.model.TimerNote
import com.mny.mojito.widget.pkg.model.Today

/**
 * WidgetDatabase
 * @author caicai
 * Created on 2021-09-07 15:34
 * Desc:
 */
@Database(entities = [Motto::class, Launcher::class, Today::class, TimerNote::class], version = 1)
abstract class WidgetDatabase : RoomDatabase() {

    abstract fun mottoDao(): MottoDao
    abstract fun todayDao(): TodayDao
    abstract fun timerNoteDao(): TimerNoteDao
    abstract fun launcherDao(): LauncherDao

    companion object {
        private var mInstance: WidgetDatabase? = null
        private val lock: Any = Any()
        fun db(): WidgetDatabase {
            val name = "MojitoWidget"
            synchronized(lock) {
                if (mInstance == null) {
                    mInstance =
                        Room.databaseBuilder(Utils.getApp(), WidgetDatabase::class.java, name)
                            .fallbackToDestructiveMigration()
                            .build()

                }
            }
            return mInstance!!
        }
    }
}