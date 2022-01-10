package com.mny.mojito.widget.pkg.presentation.timer

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.mny.mojito.base.BaseVBFragment
import com.mny.mojito.entension.collectOnLifecycle
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.mojito.entension.visibleOrGone
import com.mny.mojito.widget.pkg.databinding.FragmentTimerNoteAddOrEditBinding
import com.mny.mojito.widget.pkg.helper.showDatePicker
import com.mny.mojito.widget.pkg.helper.showSaveDialog
import com.mny.mojito.widget.pkg.ktx.date
import com.mny.mojito.widget.pkg.model.TimerNote
import com.mny.mojito.widget.pkg.model.dayCountOrProgress
import com.mny.mojito.widget.pkg.model.desc
import com.mny.mojito.widget.pkg.model.isCountDownMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimerNoteAddOrEditFragment : BaseVBFragment<FragmentTimerNoteAddOrEditBinding>() {

    private val mViewModel by activityViewModels<TimerNoteViewModel>()

    override fun initView(view: View) {
        super.initView(view)
        mBinding.ivSwitchMode.setOnDebouncedClickListener {
            mViewModel.switchMode()
        }
        mBinding.rivName.setTitle("名称")
        mBinding.rivName.setInfo("累计日")
        mBinding.rivStartTime.setTitle("起始日期")
        mBinding.rivTargetTime.setTitle("目标日期")

        mBinding.rivName.setOnDebouncedClickListener {
            mActivity?.showSaveDialog("请输入名称", mBinding.rivName.getInfo(), "请输入名称") {
                mViewModel.updateName(it)
            }
        }

        mBinding.rivStartTime.setOnDebouncedClickListener {
            mActivity?.showDatePicker(mViewModel.getEditStartTime()) {
                mViewModel.updateStartTime(it)
            }
        }
        mBinding.rivTargetTime.setOnDebouncedClickListener {
            mActivity?.showDatePicker(mViewModel.getEditTargetTime()) {
                mViewModel.updateTargetTime(it)
            }
        }

        mBinding.tvSave.setOnDebouncedClickListener {
            mViewModel.saveNote()
            findNavController().popBackStack()
        }

        mBinding.tvDelete.setOnDebouncedClickListener {
            mViewModel.deleteNote()
            findNavController().popBackStack()
        }
    }

    override fun initOnceObserver(lifecycleOwner: LifecycleOwner) {
        super.initOnceObserver(lifecycleOwner)
        collectOnLifecycle(mViewModel.editNote) { note: TimerNote ->
            with(mBinding.cellPreview) {
                tvDesc.text = note.desc
                tvName.text = note.name
                mBinding.rivName.setInfo(note.name)
                mBinding.rivStartTime.setInfo(note.startTime.date)
                mBinding.rivTargetTime.setInfo(note.targetTime.date)
                tvDayCountOrProgress.text = note.dayCountOrProgress
                mBinding.rivStartTime.visibleOrGone(note.isCountDownMode)
            }
        }
    }

}