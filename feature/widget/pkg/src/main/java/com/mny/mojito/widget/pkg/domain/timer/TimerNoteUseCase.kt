package com.mny.mojito.widget.pkg.domain.timer

import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.widget.pkg.data.local.TimerNoteDao
import com.mny.mojito.widget.pkg.model.TimerNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * TimerUseCase
 * @author caicai
 * Created on 2021-09-07 15:55
 * Desc:
 */
@Singleton
class TimerNoteUseCase @Inject constructor(private val mDao: TimerNoteDao) {

    suspend fun insert(note: TimerNote): Long = withContext(Dispatchers.IO) {
        LogUtils.d(note)
        val result = mDao.insert(note)
        result
    }

    suspend fun deleteTimerNoteById(id: Long) = withContext(Dispatchers.IO) {
        LogUtils.d("$id")
        mDao.deleteById(id)
    }

    suspend fun update(note: TimerNote) = withContext(Dispatchers.IO) {
        LogUtils.d(note)
        mDao.update(note)
    }

    fun fetchAllTimerNote() = mDao.fetchTimeNoteList()

}