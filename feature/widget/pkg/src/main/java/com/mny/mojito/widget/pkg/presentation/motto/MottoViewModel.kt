package com.mny.mojito.widget.pkg.presentation.motto

import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.Utils
import com.mny.mojito.mvvm.BaseNoStateViewModel
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
 * MottoViewModel
 * Desc:
 */
@HiltViewModel
class MottoViewModel @Inject constructor(private val mUseCase: MottoUseCase) :
    BaseNoStateViewModel() {

    private val mMottoModCount = MutableStateFlow<Int>(0)
    val mottoModCount: StateFlow<Int> = mMottoModCount
    val mMottos = mutableListOf<Motto>()

    private val mEditMotto = MutableStateFlow<Motto?>(null)
    val editMotto: StateFlow<Motto?> = mEditMotto

    fun initMottos() {
        viewModelScope.launch(Dispatchers.IO) {
            mUseCase.fetchAllMottos()
                .collect {
                    LogUtils.d("Motto initMottos modCount - ${mMottoModCount.value} - list = $it")
                    mMottos.clear()
                    mMottos.addAll(it)
                    updateMottos()
                }
        }
    }

    private fun updateMottos() {
        mMottoModCount.value += 1
    }

    fun setEditMotto(editMotto: Motto?) {
        LogUtils.d("setEditMotto $editMotto")
        mEditMotto.value = editMotto
    }

    fun saveMotto() {
        LogUtils.d("saveMotto ${mEditMotto.value}")
        if (mEditMotto.value == null) return
        val motto = mEditMotto.value!!
        viewModelScope.launch {
            motto.updateTime = TimeUtils.getNowMills()
            if (motto.id == 0L) {
                motto.id = mUseCase.insert(motto)
                LogUtils.d("saveMotto insertedId = ${motto.id}")
            } else {
                mUseCase.update(motto)
                MottoWidgetUpdate.updateAppWidget(Utils.getApp())
            }
            updateMottos()
        }
    }

    fun deleteMotto() {
        LogUtils.d("deleteMotto ${mEditMotto.value}")
        if (mEditMotto.value == null) return
        val motto = mEditMotto.value!!
        viewModelScope.launch {
            mUseCase.deleteMottoById(motto.id)
            updateMottos()
            MottoWidgetUpdate.updateAppWidget(Utils.getApp())
        }
    }

    fun updateMotto(motto: String) {
        val oldMotto = mEditMotto.value
        val newMotto =
            oldMotto?.copy(content = motto.trim())?.apply { id = oldMotto.id }
                ?: Motto(content = motto.trim(), createTime = TimeUtils.getNowMills())
        newMotto.update()
        mEditMotto.value = newMotto
    }


}