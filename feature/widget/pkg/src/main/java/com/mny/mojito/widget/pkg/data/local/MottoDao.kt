package com.mny.mojito.widget.pkg.data.local

import androidx.room.*
import com.mny.mojito.widget.pkg.model.Motto
import kotlinx.coroutines.flow.Flow

/**
 * MottoDao
 * @author caicai
 * Created on 2021-09-07 15:40
 * Desc:
 */
@Dao
interface MottoDao {
    @Query("select * from motto order by createTime DESC")
    fun fetchAllMottos(): Flow<List<Motto>>

    @Query("select * from motto order by createTime DESC limit 1")
    fun fetchNewestMotto(): List<Motto>

    @Query("select * from motto where id = :id")
    fun fetchMottoById(id: Long): Motto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(motto: Motto): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(motto: Motto)

    @Query("delete from motto where id = :id")
    fun deleteById(id: Long)
}