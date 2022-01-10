package com.mny.mojito.widget.pkg.domain.today

import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.widget.pkg.data.local.MottoDao
import com.mny.mojito.widget.pkg.data.local.TodayDao
import com.mny.mojito.widget.pkg.model.Motto
import com.mny.mojito.widget.pkg.model.Today
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MottoUsecase
 * @author caicai
 * Created on 2021-09-07 15:55
 * Desc:
 */
@Singleton
class TodayUseCase @Inject constructor(private val mDao: TodayDao) {

    suspend fun insert(today: Today): Long = withContext(Dispatchers.IO) {
        LogUtils.d(today)
        val result = mDao.insert(today)
        result
    }

    suspend fun deleteTodayById(id: Long) = withContext(Dispatchers.IO) {
        LogUtils.d("$id")
        mDao.deleteById(id)
    }

    suspend fun update(today: Today) = withContext(Dispatchers.IO) {
        LogUtils.d(today)
        mDao.update(today)
    }

    fun fetchTodayList() = mDao.fetchTodayList()

}