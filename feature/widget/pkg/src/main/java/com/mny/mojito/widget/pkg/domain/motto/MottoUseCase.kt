package com.mny.mojito.widget.pkg.domain.motto

import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.widget.pkg.data.local.MottoDao
import com.mny.mojito.widget.pkg.model.Motto
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
class MottoUseCase @Inject constructor(private val mDao: MottoDao) {

    suspend fun insert(motto: Motto): Long = withContext(Dispatchers.IO) {
        LogUtils.d(motto)
        val result = mDao.insert(motto)
        result
    }

    suspend fun deleteMottoById(id: Long) = withContext(Dispatchers.IO) {
        LogUtils.d("$id")
        mDao.deleteById(id)
    }

    suspend fun update(motto: Motto) = withContext(Dispatchers.IO) {
        LogUtils.d(motto)
        mDao.update(motto)
    }

    fun fetchAllMottos() = mDao.fetchAllMottos()

}