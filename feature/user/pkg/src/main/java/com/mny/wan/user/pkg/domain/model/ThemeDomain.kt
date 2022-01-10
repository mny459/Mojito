package com.mny.wan.user.pkg.domain.model

import android.os.SystemClock

/**
 * ThemeDomain
 * @author caicai
 * Created on 2021-10-27 16:25
 * Desc:
 */
data class ThemeDomain(
    var followSystem: Boolean = true,
    var darkMode: Boolean = false,
    var updateTime: Long = SystemClock.elapsedRealtime()
) {
    fun update() {
        updateTime = SystemClock.elapsedRealtime()
    }
}
