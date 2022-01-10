package com.mny.mojito.widget.pkg.presentation.timer

import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.mny.mojito.mvvm.BaseNoStateViewModel
import com.mny.mojito.widget.pkg.data.local.WidgetHelper
import com.mny.mojito.widget.pkg.domain.timer.TimerNoteUseCase
import com.mny.mojito.widget.pkg.model.TimerNote
import com.mny.mojito.widget.pkg.update.TimerNoteWidgetUpdate
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
class TimerNotePickerViewModel @Inject constructor(private val mUseCase: TimerNoteUseCase) :
    BaseNoStateViewModel() {

    private val mListModCount = MutableStateFlow(0)
    val listModCount: StateFlow<Int> = mListModCount
    private val mDataList = mutableListOf<TimerNote>()
    var mWidgetId: Int = 0

    fun getNewDataList() = mDataList.toList()

    fun initData() {
        viewModelScope.launch(Dispatchers.IO) {
            mUseCase.fetchAllTimerNote()
                .collect {
                    LogUtils.d(it)
                    mDataList.clear()
                    mDataList.addAll(it)
                    updateList()
                }
        }
    }

    private fun updateList() {
        mListModCount.value += 1
    }

    fun initWidgetId(widgetId: Int) {
        LogUtils.d("initWidgetId $widgetId")
        mWidgetId = widgetId
    }

    fun saveMotto(note: TimerNote) {
        LogUtils.d("widgetId = $mWidgetId $note")
        WidgetHelper.saveTimerNoteByWidgetId(mWidgetId, note.id)
        TimerNoteWidgetUpdate.updateAppWidget(Utils.getApp(), mWidgetId)
    }
}