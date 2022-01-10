package com.mojito.common.helper

import android.content.Context
import android.content.res.Configuration
import android.util.TypedValue
import androidx.appcompat.app.AppCompatDelegate
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.Utils

/**
 * 当前是否是夜间模式
 */
fun Context.isNightMode(): Boolean {
    val config = resources.configuration
    val uiMode = config.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return uiMode == Configuration.UI_MODE_NIGHT_YES
}

object ThemeHelper {
    fun setLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun setNightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    fun setSystemMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    fun getColorByAttr(attrId: Int): Int {
        val typedValue = TypedValue()
        ActivityUtils.getTopActivity().theme.resolveAttribute(attrId, typedValue, true)
        return typedValue.data
    }
}

