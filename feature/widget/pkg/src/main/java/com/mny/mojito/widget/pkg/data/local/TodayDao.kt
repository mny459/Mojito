package com.mny.mojito.widget.pkg.data.local

import androidx.room.*
import com.mny.mojito.widget.pkg.model.Today
import kotlinx.coroutines.flow.Flow

/**
 * TodayDao
 * @author caicai
 * Created on 2021-09-07 15:40
 * Desc:
 */
@Dao
interface TodayDao {
    @Query("select * from today order by createTime DESC")
    fun fetchTodayList(): Flow<List<Today>>

    @Query("select * from today order by createTime DESC limit 1")
    fun fetchNewestToday(): List<Today>

    @Query("select * from today where id = :id")
    fun fetchTodayById(id: Long): Today?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(today: Today): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(today: Today)

    @Query("delete from today where id = :id")
    fun deleteById(id: Long)
}