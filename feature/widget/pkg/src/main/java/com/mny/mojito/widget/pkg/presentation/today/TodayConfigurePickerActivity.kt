package com.mny.mojito.widget.pkg.presentation.today

import android.appwidget.AppWidgetManager
import android.os.Bundle
import androidx.activity.viewModels
import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.base.BaseVBActivity
import com.mny.mojito.widget.pkg.databinding.ActivityTimerNotePickerBinding
import com.mny.mojito.widget.pkg.databinding.ActivityTodayPickerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodayConfigurePickerActivity : BaseVBActivity<ActivityTodayPickerBinding>() {

    private val mViewModel by viewModels<TodayPickerViewModel>()

    private var mAppWidgetId: Int = 0

    override fun initArgs(bundle: Bundle?, savedInstanceState: Bundle?): Boolean {
        setResult(RESULT_CANCELED)
        return super.initArgs(bundle, savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mAppWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID
        LogUtils.d("$mAppWidgetId")
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }
        mViewModel.initWidgetId(mAppWidgetId)
    }

}