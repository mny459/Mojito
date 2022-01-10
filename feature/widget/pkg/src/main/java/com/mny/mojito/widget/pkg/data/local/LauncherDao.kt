package com.mny.mojito.widget.pkg.data.local

import androidx.room.*
import com.mny.mojito.widget.pkg.model.Launcher
import kotlinx.coroutines.flow.Flow

/**
 * MottoDao
 * @author caicai
 * Created on 2021-09-07 15:40
 * Desc:
 */
@Dao
interface LauncherDao {
    @Query("select * from launcher order by createTime DESC")
    fun fetchAllLaunchers(): Flow<List<Launcher>>

    @Query("select * from launcher order by createTime DESC limit 1")
    fun fetchNewestLauncher(): List<Launcher>

    @Query("select * from launcher where id = :id")
    fun fetchLauncherById(id: Long): Launcher?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(launcher: Launcher): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(launcher: Launcher)

    @Query("delete from motto where id = :id")
    fun deleteById(id: Long)
}