package com.mojito.common.data.local

import com.blankj.utilcode.util.GsonUtils
import com.mny.mojito.data.kv.KVHelper
import com.mojito.common.data.remote.model.BeanCoin
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * UserDelegate
 * Desc:
 */
class UserCoinDelegate : ReadWriteProperty<UserHelper, BeanCoin?> {

    companion object {
        private const val EMPTY_STR = ""
        private const val KEY_USER_COIN_INFO = "user_coin_info"
    }

    private var mUser: BeanCoin? = null

    override fun getValue(thisRef: UserHelper, property: KProperty<*>): BeanCoin? {
        if (mUser != null) return mUser
        val userInfo = KVHelper.getString(KEY_USER_COIN_INFO, EMPTY_STR)
        mUser = if (userInfo.isEmpty()) null
        else GsonUtils.fromJson(userInfo, BeanCoin::class.java)
        return mUser
    }

    override fun setValue(thisRef: UserHelper, property: KProperty<*>, value: BeanCoin?) {
        saveCoin(value)
        thisRef.updateUserCoin(value)
        mUser = value
    }

    private fun saveCoin(data: BeanCoin? = null) {
        KVHelper.put(KEY_USER_COIN_INFO, if (data == null) EMPTY_STR else GsonUtils.toJson(data))
    }
}