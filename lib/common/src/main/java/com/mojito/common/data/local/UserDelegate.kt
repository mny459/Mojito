package com.mojito.common.data.local

import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.data.kv.KVHelper
import com.mojito.common.data.remote.model.BeanUserInfo
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * UserDelegate
 * Desc:
 */
class UserDelegate : ReadWriteProperty<UserHelper, BeanUserInfo?> {

    companion object {
        private const val EMPTY_STR = ""
        private const val KEY_USER_INFO = "user_info"
    }

    private var mUser: BeanUserInfo? = null

    override fun getValue(thisRef: UserHelper, property: KProperty<*>): BeanUserInfo? {
        LogUtils.v("UserDelegate getValue $mUser")
        if (mUser != null) return mUser
        val userInfo = KVHelper.getString(KEY_USER_INFO, EMPTY_STR)
        mUser = if (userInfo.isEmpty()) null
        else GsonUtils.fromJson(userInfo, BeanUserInfo::class.java)
        LogUtils.d("UserDelegate getValue init $mUser")
        return mUser
    }

    override fun setValue(thisRef: UserHelper, property: KProperty<*>, value: BeanUserInfo?) {
        saveUserInfo(value)
        if (value != mUser) {
            thisRef.updateUser(value)
        }
        LogUtils.d("UserDelegate setValue $value")
        mUser = value
    }

    private fun saveUserInfo(info: BeanUserInfo?) {
        KVHelper.put(KEY_USER_INFO, if (info == null) EMPTY_STR else GsonUtils.toJson(info))
    }

}