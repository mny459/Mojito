package com.mny.wan.user.pkg.presentation.setting

import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LanguageUtils
import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.mvvm.BaseNoStateViewModel
import com.mny.wan.user.pkg.domain.model.ThemeDomain
import com.mojito.common.helper.SettingHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * TODO: 目前切换语言和暗黑模式都是重启应用，以后看看怎么改成只需重启Activity
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(private val mSettingHelper: SettingHelper) :
    BaseNoStateViewModel() {

    private val mTheme = MutableStateFlow(ThemeDomain())
    val theme: StateFlow<ThemeDomain> = mTheme

    private val mUseEnglishLanguage = MutableStateFlow(false)
    val useEnglishLanguage: StateFlow<Boolean> = mUseEnglishLanguage

    private val mUseX5 = MutableStateFlow(false)
    val useX5: StateFlow<Boolean> = mUseX5

    fun init() {
        val themeFollowSystem = mSettingHelper.isThemeFollowSystem()
        val themeDark = if (themeFollowSystem) false
        else mSettingHelper.isThemeDark()
        mTheme.value = ThemeDomain(followSystem = themeFollowSystem, darkMode = themeDark)
        mUseEnglishLanguage.value = LanguageUtils.isAppliedLanguage(Locale.ENGLISH)
        mUseX5.value = mSettingHelper.isUseX5()
        LogUtils.d("Setting.View 初始化主题配置 是否跟随系统主题 $themeFollowSystem 是否采用深色模式 $themeDark 是否使用英文 ${mUseEnglishLanguage.value}")
    }

    fun switchThemeFollowSystem() {
        viewModelScope.launch {
            delay(300)
            val oldTheme = mTheme.value
            val isFollow = oldTheme.followSystem
            if (isFollow) {
                mSettingHelper.setThemeFollowAppSetting()
            } else {
                mSettingHelper.setThemeFollowSystem()
            }
            AppUtils.relaunchApp(true)
        }
    }

    fun switchThemeDark() {
        val oldTheme = mTheme.value
        if (oldTheme.followSystem) return
        viewModelScope.launch {
            val dark = oldTheme.darkMode
            mTheme.value = oldTheme.copy(darkMode = !dark).apply {
                update()
            }
            delay(300)
            if (dark) mSettingHelper.setThemeLight()
            else mSettingHelper.setThemeDark()
            recreate()
        }
    }

    fun switchLanguage() {
        viewModelScope.launch {
            val appliedEnglish = LanguageUtils.isAppliedLanguage(Locale.ENGLISH)
            mUseEnglishLanguage.value = !appliedEnglish
            delay(300)
            if (appliedEnglish) {
                LanguageUtils.applySystemLanguage(false)
            } else {
                LanguageUtils.applyLanguage(Locale.ENGLISH, false)
            }
        }
    }

    private fun recreate() {
        viewModelScope.launch(Dispatchers.Main) {
            ActivityUtils.getActivityList()?.apply {
                for (activity in this) {
                    activity.recreate()
                }
            }
        }
    }

    fun switchUseX5() {
        mSettingHelper.switchUseX5()
        mUseX5.value = mSettingHelper.isUseX5()
    }

}