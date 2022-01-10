package com.mny.mojito.widget.pkg.presentation.timer

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mny.mojito.base.BaseVBFragment
import com.mny.mojito.entension.collectOnLifecycle
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.mojito.widget.pkg.databinding.FragmentTimerNoteBinding
import com.mny.mojito.widget.pkg.model.TimerNote
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TimerNoteFragment : BaseVBFragment<FragmentTimerNoteBinding>() {

    private val mViewModel by activityViewModels<TimerNoteViewModel>()

    @Inject
    lateinit var mAdapter: TimerNoteAdapter

    override fun initView(view: View) {
        super.initView(view)
        mBinding.rvList.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

        mBinding.fabAddToday.setOnDebouncedClickListener {
            mViewModel.setEditNote(TimerNote.createDefaultNote())
            findNavController().navigate(TimerNoteFragmentDirections.actionToTimerNoteAddOrEdit())
        }

        mAdapter.mOnMottoClickedListener = {
            mViewModel.setEditNote(it)
            findNavController().navigate(TimerNoteFragmentDirections.actionToTimerNoteAddOrEdit())
        }
    }

    override fun onFirstInit() {
        super.onFirstInit()
        mViewModel.initNotes()
    }

    override fun initOnceObserver(lifecycleOwner: LifecycleOwner) {
        super.initOnceObserver(lifecycleOwner)
        collectOnLifecycle(mViewModel.listModCount) {
            mAdapter.submitList(mViewModel.getNewList())
        }
    }
}