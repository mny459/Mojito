package com.mny.mojito.widget.pkg.di

import com.mny.mojito.widget.pkg.data.local.LauncherDao
import com.mny.mojito.widget.pkg.data.local.MottoDao
import com.mny.mojito.widget.pkg.data.local.TimerNoteDao
import com.mny.mojito.widget.pkg.data.local.TodayDao
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope

/**
 * WidgetDaoEntryPoint
 * @author caicai
 * Created on 2021-11-05 16:27
 * Desc:
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetDaoEntryPoint {
    fun mottoDao(): MottoDao
    fun todayDao(): TodayDao
    fun timerNoteDao(): TimerNoteDao
    fun launcherDao(): LauncherDao
    fun getCoroutineScope(): CoroutineScope
}
