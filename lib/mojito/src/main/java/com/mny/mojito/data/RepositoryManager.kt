package com.mny.mojito.data

import com.mny.mojito.data.kv.KVHelper
import retrofit2.Retrofit
import javax.inject.Inject

/**
 *@author mny on 2020/5/17.
 *        Email：mny9@outlook.com
 *        Desc:
 */
class RepositoryManager @Inject constructor() : IRepository {

    @Inject
    lateinit var mRetrofit: Retrofit

    override fun <T> obtainRetrofitService(serviceClass: Class<T>): T {
        return mRetrofit.create(serviceClass)
    }

    override fun obtainKVHelper(id: String, rootPath: String): KVHelper {
        return KVHelper.getInstance(id, rootPath)
    }
}