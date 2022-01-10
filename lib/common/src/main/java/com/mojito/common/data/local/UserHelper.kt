package com.mojito.common.data.local

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.mny.mojito.data.kv.KVHelper
import com.mojito.common.data.remote.model.BeanCoin
import com.mojito.common.data.remote.model.BeanUserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @Author CaiRj
 * @Date 2019/10/23 14:15
 * @Desc
 */
object UserHelper {
    private const val EMPTY_STR = ""
    private const val KEY_TOKEN_PASS = "token_pass"
    private const val KEY_LOGIN_STATUS = "login_status"

    private var mIsLogin = false
    private var mTokenPass: String = EMPTY_STR

    private val mUser = MutableStateFlow<BeanUserInfo?>(null)
    val userFlow: StateFlow<BeanUserInfo?> = mUser
    private val mUserCoin = MutableStateFlow<BeanCoin?>(null)
    val userCoinFlow: StateFlow<BeanCoin?> = mUserCoin
    var user by UserDelegate()
    var userCoin by UserCoinDelegate()

    fun updateUser(value: BeanUserInfo?) {
        value?.updateTime = TimeUtils.getNowMills()
        mUser.value = value
    }

    fun updateUserCoin(value: BeanCoin?) {
        value?.updateTime = TimeUtils.getNowMills()
        mUserCoin.value = value
    }

    fun login(info: BeanUserInfo) {
        user = info
        mIsLogin = true
        KVHelper.put(KEY_LOGIN_STATUS, true)
    }

    fun saveTokenPass(tokenPass: String) {
        mTokenPass = tokenPass
        KVHelper.put(KEY_TOKEN_PASS, tokenPass)
    }

    fun loginOut() {
        clearUserInfo()
        clearTokenPass()
        KVHelper.put(KEY_LOGIN_STATUS, false)
    }

    private fun clearUserInfo() {
        user = null
        userCoin = null
        mIsLogin = false
    }

    private fun clearTokenPass() {
        mTokenPass = EMPTY_STR
        KVHelper.put(KEY_TOKEN_PASS, EMPTY_STR)
    }

    fun getTokenPass(): String = mTokenPass

    fun isLogin(): Boolean = mIsLogin

    fun initUserInfo() {
        mIsLogin = KVHelper.getBool(KEY_LOGIN_STATUS, false)
        val tokenPass = KVHelper.getString(KEY_TOKEN_PASS, EMPTY_STR)
        mTokenPass = tokenPass
        mUser.value = user
        mUserCoin.value = userCoin
        LogUtils.d("initUserInfo mIsLogin = $mIsLogin tokenPass = $tokenPass")
    }

}