package com.mny.mojito.widget.pkg.helper

import android.app.Activity
import android.content.Context
import com.blankj.utilcode.util.TimeUtils
import com.lxj.xpopup.XPopup

import com.github.gzuliyujiang.calendarpicker.CalendarPicker
import com.github.gzuliyujiang.wheelpicker.DatePicker
import com.github.gzuliyujiang.wheelpicker.OptionPicker
import com.github.gzuliyujiang.wheelpicker.annotation.DateMode
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener
import com.github.gzuliyujiang.wheelpicker.contract.OnOptionPickedListener
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity
import com.github.gzuliyujiang.wheelpicker.impl.UnitDateFormatter
import com.github.gzuliyujiang.wheelpicker.widget.DateWheelLayout
import com.mny.mojito.widget.pkg.R


/**
 * DialogHelper
 * @author caicai
 * Created on 2021-11-06 13:39
 * Desc:
 */
object DialogHelper {
    fun showInputDialog(
        context: Context,
        title: String,
        inputContent: String,
        hint: String,
        onConfirmClickListener: ((text: String) -> Unit)
    ) {
        XPopup.Builder(context)
            .hasStatusBarShadow(false) //.dismissOnBackPressed(false)
            .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
            .autoOpenSoftInput(true)
            .isDarkTheme(true)
            .isViewMode(true)
            //.autoFocusEditText(false) //是否让弹窗内的EditText自动获取焦点，默认是true
            //.moveUpToKeyboard(false)   //是否移动到软键盘上面，默认为true
            .asInputConfirm(
                title, null, inputContent, hint
            ) {
                onConfirmClickListener.invoke(it)
            }
            .show()
    }

    fun showDatePicker(
        activity: Activity,
        selectedDate: Long,
        onConfirmClickListener: ((time: Long) -> Unit)
    ) = DatePicker(activity).apply {
        setOnDatePickedListener { year, month, day ->
            onConfirmClickListener.invoke(TimeUtils.string2Millis("$year-$month-$day 00:00:00"))
        }
        wheelLayout.setDateMode(DateMode.YEAR_MONTH_DAY)
        wheelLayout.setDateFormatter(UnitDateFormatter())
        val now = TimeUtils.millis2Date(selectedDate)
        wheelLayout.setRange(
            DateEntity.target(1000, 1, 1),
            DateEntity.target(3000, 12, 31),
            DateEntity.target(now.year + 1900, now.month + 1, now.date)
        )
        wheelLayout.setCurtainEnabled(true)
        show()
    }

    fun <T> showPicker(
        activity: Activity,
        data: List<T>,
        defaultPosition: Int,
        onOptionPicked: (position: Int, item: T) -> Unit
    ) = OptionPicker(activity).apply {
        setData(data)
        setDefaultPosition(defaultPosition)
        setOnOptionPickedListener { position, item ->
            onOptionPicked.invoke(
                position,
                item as T
            )
        }
        show()
    }

    fun showMonthDayPicker(
        activity: Activity,
        defaultTime: Long,
        onPicked: (month: Int, day: Int) -> Unit
    ) {
        val picker = DatePicker(activity)
        picker.wheelLayout.apply {
            setDateMode(DateMode.MONTH_DAY)
            setDateFormatter(UnitDateFormatter())
            val date = TimeUtils.millis2Date(defaultTime)
            setDefaultValue(DateEntity.target(date.year + 1900, date.month + 1, date.date))
        }
        picker.setBodyWidth(200)
        picker.setOnDatePickedListener { _, month, day -> onPicked.invoke(month, day) }
        picker.show()
    }
}

fun Context.showSaveDialog(
    title: String,
    inputContent: String,
    hint: String,
    onConfirmClickListener: ((text: String) -> Unit)
) {
    DialogHelper.showInputDialog(this, title, inputContent, hint, onConfirmClickListener)
}

fun Activity.showDatePicker(
    selectedDate: Long,
    onConfirmClickListener: ((time: Long) -> Unit)
) {
    DialogHelper.showDatePicker(this, selectedDate, onConfirmClickListener)
}

fun <T> Activity.showPicker(
    data: List<T>,
    defaultPosition: Int,
    onOptionPicked: (position: Int, item: T) -> Unit
) {
    DialogHelper.showPicker(this, data, defaultPosition, onOptionPicked)
}

fun Activity.showMonthDayPicker(
    defaultTime: Long,
    onPicked: (month: Int, day: Int) -> Unit
) {
    DialogHelper.showMonthDayPicker(this, defaultTime, onPicked)
}