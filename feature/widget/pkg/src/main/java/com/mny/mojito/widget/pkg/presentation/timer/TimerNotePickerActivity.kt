package com.mny.mojito.widget.pkg.presentation.timer

import android.os.Bundle
import androidx.activity.viewModels
import com.mny.mojito.base.BaseVBActivity
import com.mny.mojito.widget.pkg.databinding.ActivityTimerNotePickerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimerNotePickerActivity : BaseVBActivity<ActivityTimerNotePickerBinding>() {

    private val mViewModel by viewModels<TimerNotePickerViewModel>()

    private var mAppId: Int = 0

    override fun initArgs(bundle: Bundle?, savedInstanceState: Bundle?): Boolean {
        mAppId = bundle?.getInt("appId", 0) ?: 0
        return super.initArgs(bundle, savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mViewModel.initWidgetId(mAppId)
    }

}