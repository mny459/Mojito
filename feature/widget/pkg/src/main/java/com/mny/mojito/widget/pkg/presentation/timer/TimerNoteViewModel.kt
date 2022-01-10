package com.mny.mojito.widget.pkg.presentation.timer

import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.Utils
import com.mny.mojito.mvvm.BaseNoStateViewModel
import com.mny.mojito.widget.pkg.domain.timer.TimerNoteUseCase
import com.mny.mojito.widget.pkg.model.TimerNote
import com.mny.mojito.widget.pkg.model.TimerNoteMode
import com.mny.mojito.widget.pkg.update.TimerNoteWidgetUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * MottoViewModel
 * Desc:
 */
@HiltViewModel
class TimerNoteViewModel @Inject constructor(private val mUseCase: TimerNoteUseCase) :
    BaseNoStateViewModel() {

    private val mListModCount = MutableStateFlow<Int>(0)
    val listModCount: StateFlow<Int> = mListModCount
    private val mNoteList = mutableListOf<TimerNote>()

    private val mEditNote = MutableStateFlow(TimerNote())
    val editNote: StateFlow<TimerNote> = mEditNote
    private val mTimerCountDownMode = MutableStateFlow(false)
    val timerCountDownMode: StateFlow<Boolean> = mTimerCountDownMode


    fun getNewList() = mNoteList.toList()
    fun getEditStartTime() = mEditNote.value.startTime
    fun getEditTargetTime() = mEditNote.value.targetTime
    fun initNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            mUseCase.fetchAllTimerNote()
                .collect {
                    LogUtils.d("Motto initMottos modCount - ${mListModCount.value} - list = $it")
                    mNoteList.clear()
                    mNoteList.addAll(it)
                    updateList()
                }
        }
    }

    private fun updateList() {
        mListModCount.value += 1
    }

    fun setEditNote(note: TimerNote) {
        LogUtils.d("setEditNote $note")
        mEditNote.value = note
    }

    fun saveNote() {
        LogUtils.d("saveNote ${mEditNote.value}")
        val data = mEditNote.value
        viewModelScope.launch {
            data.updateTime = TimeUtils.getNowMills()
            if (data.id == 0L) {
                data.id = mUseCase.insert(data)
                LogUtils.d("saveNote insertedId = ${data.id}")
            } else {
                mUseCase.update(data)
                TimerNoteWidgetUpdate.updateAppWidget(Utils.getApp())
            }
            updateList()
        }
    }

    fun deleteNote() {
        LogUtils.d("deleteMotto ${mEditNote.value}")
        if (mEditNote.value.id == 0L) return
        val note = mEditNote.value
        viewModelScope.launch {
            mUseCase.deleteTimerNoteById(note.id)
            updateList()
            TimerNoteWidgetUpdate.updateAppWidget(Utils.getApp())
        }
    }

    fun updateName(name: String) {
        val old = mEditNote.value
        val new = old.copy(name = name.trim()).apply { id = old.id }
        new.update()
        mEditNote.value = new
    }

    fun updateStartTime(time: Long) {
        val old = mEditNote.value
        if (time > old.targetTime) return
        val new = old.copy(startTime = time).apply { id = old.id }
        new.update()
        mEditNote.value = new
    }

    fun updateTargetTime(time: Long) {
        val old = mEditNote.value
        if (time < old.startTime) return
        val new = old.copy(targetTime = time).apply { id = old.id }
        new.update()
        mEditNote.value = new
    }

    fun switchMode() {
        val old = mEditNote.value
        val newFormat = when (old.mode) {
            TimerNoteMode.TOTAL -> TimerNoteMode.COUNT_DOWN
            TimerNoteMode.COUNT_DOWN -> TimerNoteMode.TOTAL
        }
        val new = old.copy(mode = newFormat).apply { id = old.id }
        new.update()
        mEditNote.value = new
    }

}