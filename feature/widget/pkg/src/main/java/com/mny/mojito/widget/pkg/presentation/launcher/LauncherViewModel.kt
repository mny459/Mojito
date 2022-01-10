package com.mny.mojito.widget.pkg.presentation.launcher

import android.content.Context
import com.mny.mojito.mvvm.BaseNoStateViewModel
import com.mny.mojito.widget.pkg.model.Launcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import android.content.res.AssetManager
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.Utils
import com.mny.mojito.widget.pkg.model.Launchers
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder


/**
 * LauncherViewModel
 * @author caicai
 * Created on 2021-09-09 14:01
 * Desc:
 */
@HiltViewModel
class LauncherViewModel @Inject constructor() :
    BaseNoStateViewModel() {
    private val mLauncherModCount = MutableStateFlow(0)
    val launcherModCount: StateFlow<Int> = mLauncherModCount
    val mPreviewLaunchers = mutableListOf<Launcher>()

    fun initPreviewLaunchers() {
        viewModelScope.launch(Dispatchers.IO) {
            val json = getJson()
            val launchers = GsonUtils.getGson().fromJson(json, Launchers::class.java)
            launchers.data.forEach {
                mPreviewLaunchers.add(it)
            }
            mLauncherModCount.value += 1
        }
    }

    fun getJson(): String {
        //将json数据变成字符串
        val stringBuilder = StringBuilder()
        try {
            //获取assets资源管理器
            val assetManager: AssetManager = Utils.getApp().assets
            //通过管理器打开文件并读取
            val bf = BufferedReader(InputStreamReader(assetManager.open("launcher.json")))
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }


}