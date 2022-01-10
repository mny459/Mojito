package com.mny.mojito.widget.pkg.presentation.today

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.mny.mojito.base.BaseVBFragment
import com.mny.mojito.entension.collectOnLifecycle
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.mojito.widget.pkg.databinding.FragmentTodayAddOrEditBinding
import com.mny.mojito.widget.pkg.helper.showMonthDayPicker
import com.mny.mojito.widget.pkg.helper.showPicker
import com.mny.mojito.widget.pkg.helper.showSaveDialog
import com.mny.mojito.widget.pkg.model.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TodayAddOrEditFragment : BaseVBFragment<FragmentTodayAddOrEditBinding>() {

    private val mViewModel by activityViewModels<TodayViewModel>()

    override fun initView(view: View) {
        super.initView(view)

        mBinding.rivName.setTitle("标签")
        mBinding.rivRepeatMode.setTitle("回答周期")
        mBinding.rivTargetTime.setTitle("目标日期")

        mBinding.rivName.setOnDebouncedClickListener {
            mActivity?.showSaveDialog("请输入名称", mBinding.rivName.getInfo(), "请输入名称") {
                mViewModel.updateLabel(it)
            }
        }

        mBinding.rivRepeatMode.setOnDebouncedClickListener {
            val position = mViewModel.getSelectedAnswerPosition()
            mActivity?.showPicker(answerModeOptions, position) { _, item ->
                mViewModel.updateAnswerMode(item.mode)
            }
        }

        mBinding.rivTargetTime.setOnDebouncedClickListener {
            val answerMode = mViewModel.editData.value.answerMode
            val position = mViewModel.getSelectedTargetTimePosition()
            when (answerMode) {
                AnswerMode.EVERY_WEEK -> {
                    mActivity?.showPicker<TodayTargetTime>(weekOptions, position) { _, item ->
                        LogUtils.d("Today.AddOrEdit pickDayOfWeek = $item position = $position")
                        mViewModel.updateTargetTime(item.targetTime())
                    }
                }
                AnswerMode.EVERY_MONTH -> {
                    mActivity?.showPicker<TodayTargetTime>(monthOptions, position) { _, item ->
                        LogUtils.d("Today.AddOrEdit pickDayOfMonth = $item position = $position")
                        mViewModel.updateTargetTime(item.targetTime())
                    }
                }
                AnswerMode.EVERY_YEAR -> {
                    mActivity?.showMonthDayPicker(mViewModel.editData.value.targetTime) { month, day ->
                        val time =
                            "${Calendar.getInstance().get(Calendar.YEAR)}-$month-$day 00:00:00"
                        LogUtils.d("Today.AddOrEdit pickDayOfYear = $time")
                        val millis = TimeUtils.string2Millis(time)
                        mViewModel.updateTargetTime(millis)
                    }
                }
            }


        }

        mBinding.tvSave.setOnDebouncedClickListener {
            mViewModel.save()
            findNavController().popBackStack()
        }

        mBinding.tvDelete.setOnDebouncedClickListener {
            mViewModel.delete()
            findNavController().popBackStack()
        }
    }

    override fun initOnceObserver(lifecycleOwner: LifecycleOwner) {
        super.initOnceObserver(lifecycleOwner)
        collectOnLifecycle(mViewModel.editData) { today: Today ->
            // LogUtils.d("Today.AddOrEdit q = ${today.q} a = ${today.a} answerMode = ${today.answerMode} formatTargetTime = ${today.formatTargetTime}")
            with(mBinding.cellPreview) {
                tvDate.text = today.now
                tvTodayQ.text = today.q
                tvTodayA.text = today.a
            }
            mBinding.rivName.setInfo(today.label)
            mBinding.rivRepeatMode.setInfo(today.answerMode.toString())
            mBinding.rivTargetTime.setInfo(today.formatTargetTime)
        }
    }
}