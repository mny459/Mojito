package com.mny.mojito.widget.pkg.presentation.motto

import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.mny.mojito.mvvm.BaseNoStateViewModel
import com.mny.mojito.widget.pkg.data.local.WidgetHelper
import com.mny.mojito.widget.pkg.domain.motto.MottoUseCase
import com.mny.mojito.widget.pkg.model.Motto
import com.mny.mojito.widget.pkg.update.MottoWidgetUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * MottoPickerViewModel
 * Desc: Motto 选择的 ViewModel
 */
@HiltViewModel
class MottoPickerViewModel @Inject constructor(private val mUseCase: MottoUseCase) :
    BaseNoStateViewModel() {

    private val mMottoModCount = MutableStateFlow(0)
    val mottoModCount: StateFlow<Int> = mMottoModCount
    val mMottos = mutableListOf<Motto>()
    var mWidgetId: Int = 0

    fun initMottos() {
        viewModelScope.launch(Dispatchers.IO) {
            mUseCase.fetchAllMottos()
                .collect {
                    LogUtils.d(it)
                    mMottos.clear()
                    mMottos.addAll(it)
                    updateMottos()
                }
        }
    }

    private fun updateMottos() {
        mMottoModCount.value += 1
    }

    fun initWidgetId(widgetId: Int) {
        LogUtils.d("initWidgetId $widgetId")
        mWidgetId = widgetId
    }

    fun saveMotto(motto: Motto) {
        LogUtils.d("widgetId = $mWidgetId $motto")
        WidgetHelper.saveMottoByWidgetId(mWidgetId, motto.id)
        MottoWidgetUpdate.updateAppWidget(Utils.getApp(), mWidgetId)
    }
}