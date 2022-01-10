package com.mny.mojito.widget.pkg.presentation.today

import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.Utils
import com.mny.mojito.mvvm.BaseNoStateViewModel
import com.mny.mojito.widget.pkg.domain.today.TodayUseCase
import com.mny.mojito.widget.pkg.model.*
import com.mny.mojito.widget.pkg.update.TodayWidgetUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * TodayViewModel
 * Desc:
 */
@HiltViewModel
class TodayViewModel @Inject constructor(private val mUseCase: TodayUseCase) :
    BaseNoStateViewModel() {

    private val mListModCount = MutableStateFlow(0)
    val listModCount: StateFlow<Int> = mListModCount
    private val mDataList = mutableListOf<Today>()

    private val mEditData = MutableStateFlow(Today.createDefaultToday())
    val editData: StateFlow<Today> = mEditData

    fun getNewestDataList() = mDataList.toList()

    fun initDataList() {
        viewModelScope.launch(Dispatchers.IO) {
            mUseCase.fetchTodayList()
                .collect {
                    LogUtils.d("Motto initMottos modCount - ${mListModCount.value} - list = $it")
                    mDataList.clear()
                    mDataList.addAll(it)
                    updateList()
                }
        }
    }

    private fun updateList() {
        mListModCount.value += 1
    }

    fun setEditData(today: Today) {
        LogUtils.d("setEditToday $today")
        mEditData.value = today
    }

    fun save() {
        LogUtils.d("save ${mEditData.value}")
        val motto = mEditData.value
        viewModelScope.launch {
            motto.updateTime = TimeUtils.getNowMills()
            if (motto.id == 0L) {
                motto.id = mUseCase.insert(motto)
                LogUtils.d("saveMotto insertedId = ${motto.id}")
            } else {
                mUseCase.update(motto)
                TodayWidgetUpdate.updateAppWidget(Utils.getApp())
            }
            updateList()
        }
    }

    fun delete() {
        LogUtils.d("delete ${mEditData.value}")
        if (mEditData.value.id == 0L) return
        val note = mEditData.value
        viewModelScope.launch {
            mUseCase.deleteTodayById(note.id)
            updateList()
            TodayWidgetUpdate.updateAppWidget(Utils.getApp())
        }
    }

    fun updateLabel(label: String) {
        val old = mEditData.value
        val new = old.copy(label = label.trim()).apply { id = old.id }
        new.update()
        mEditData.value = new
    }

    fun updateAnswerMode(mode: AnswerMode) {
        val old = mEditData.value
        val new = old.copy(answerMode = mode).apply {
            id = old.id
            targetTime = when (mode) {
                AnswerMode.EVERY_WEEK -> Calendar.getInstance().get(Calendar.DAY_OF_WEEK).toLong()
                AnswerMode.EVERY_MONTH -> Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toLong()
                AnswerMode.EVERY_YEAR -> TimeUtils.getNowMills()
            }
        }
        new.update()
        mEditData.value = new
    }

    fun updateTargetTime(time: Long) {
        val old = mEditData.value
        val new = old.copy(targetTime = time).apply { id = old.id }
        new.update()
        mEditData.value = new
    }

    fun getSelectedAnswerPosition(): Int =
        answerModeOptions.indexOfFirst { it.mode == mEditData.value.answerMode }

    fun getSelectedTargetTimePosition(): Int = when (mEditData.value.answerMode) {
        AnswerMode.EVERY_WEEK -> weekOptions.indexOfFirst { it.targetTime() == mEditData.value.targetTime }
        AnswerMode.EVERY_MONTH -> monthOptions.indexOfFirst { it.targetTime() == mEditData.value.targetTime }
        else -> 0
    }

}