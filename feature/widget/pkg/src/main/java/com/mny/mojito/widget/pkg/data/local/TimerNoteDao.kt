package com.mny.mojito.widget.pkg.data.local

import androidx.room.*
import com.mny.mojito.widget.pkg.model.TimerNote
import kotlinx.coroutines.flow.Flow

/**
 * TodayDao
 * @author caicai
 * Created on 2021-09-07 15:40
 * Desc:
 */
@Dao
interface TimerNoteDao {
    @Query("select * from timer_note order by createTime DESC")
    fun fetchTimeNoteList(): Flow<List<TimerNote>>

    @Query("select * from timer_note order by createTime DESC limit 1")
    fun fetchNewestTimeNote(): List<TimerNote>

    @Query("select * from timer_note where id = :id")
    fun fetchTimeNoteById(id: Long): TimerNote?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: TimerNote): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(note: TimerNote)

    @Query("delete from timer_note where id = :id")
    fun deleteById(id: Long)
}