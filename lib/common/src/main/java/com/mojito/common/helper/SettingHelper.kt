package com.mojito.common.helper

import androidx.appcompat.app.AppCompatDelegate
import com.mny.mojito.data.kv.KVHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingHelper @Inject constructor() {

    companion object {
        private const val KEY_THEME_MODE = "theme_model"
        private const val KEY_X5_WEB_VIEW = "x5_web_view"
        private const val MODE_NIGHT = AppCompatDelegate.MODE_NIGHT_YES
        private const val MODE_LIGHT = AppCompatDelegate.MODE_NIGHT_NO
        private const val MODE_FOLLOW_SYSTEM = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    private var mUseX5 = true

    /**
     * 暗色主题
     */
    fun setThemeDark() {
        KVHelper.put(KEY_THEME_MODE, MODE_NIGHT)
        ThemeHelper.setNightMode()
    }

    /**
     * 亮色主题
     */
    fun setThemeLight() {
        KVHelper.put(KEY_THEME_MODE, MODE_LIGHT)
        ThemeHelper.setLightMode()
    }

    /**
     * 跟随系统
     */
    fun setThemeFollowSystem() {
        KVHelper.put(KEY_THEME_MODE, MODE_FOLLOW_SYSTEM)
        ThemeHelper.setSystemMode()
    }

    /**
     * 切换为跟随 APP 的主题时，默认亮色
     */
    fun setThemeFollowAppSetting() {
        setThemeLight()
    }

    private fun getCurrentTheme() = KVHelper.getInt(KEY_THEME_MODE, MODE_FOLLOW_SYSTEM)

    fun isThemeFollowSystem() = getCurrentTheme() == MODE_FOLLOW_SYSTEM

    fun isThemeDark() = getCurrentTheme() == MODE_NIGHT

    fun initTheme() {
        when {
            isThemeFollowSystem() -> ThemeHelper.setSystemMode()
            isThemeDark() -> ThemeHelper.setNightMode()
            else -> ThemeHelper.setLightMode()
        }
        mUseX5 = KVHelper.getBool(KEY_X5_WEB_VIEW, true)
    }

    fun switchUseX5() {
        mUseX5 = !mUseX5
        KVHelper.put(KEY_X5_WEB_VIEW, mUseX5)
    }

    fun isUseX5() = mUseX5


}