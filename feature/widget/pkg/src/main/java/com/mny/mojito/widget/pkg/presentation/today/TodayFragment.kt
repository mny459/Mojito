package com.mny.mojito.widget.pkg.presentation.today

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mny.mojito.base.BaseVBFragment
import com.mny.mojito.entension.collectOnLifecycle
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.mojito.widget.pkg.databinding.FragmentTodayBinding
import com.mny.mojito.widget.pkg.model.Today
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TodayFragment : BaseVBFragment<FragmentTodayBinding>() {

    private val mViewModel by activityViewModels<TodayViewModel>()

    @Inject
    lateinit var mAdapter: TodayAdapter

    override fun initView(view: View) {
        super.initView(view)
        mBinding.rvList.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

        mBinding.fabAddToday.setOnDebouncedClickListener {
            mViewModel.setEditData(Today.createDefaultToday())
            findNavController().navigate(TodayFragmentDirections.actionToTodayAddOrEdit())
        }

        mAdapter.mOnMottoClickedListener = {
            mViewModel.setEditData(it)
            findNavController().navigate(TodayFragmentDirections.actionToTodayAddOrEdit())
        }
    }

    override fun onFirstInit() {
        super.onFirstInit()
        mViewModel.initDataList()
    }

    override fun initOnceObserver(lifecycleOwner: LifecycleOwner) {
        super.initOnceObserver(lifecycleOwner)
        collectOnLifecycle(mViewModel.listModCount) {
            mAdapter.submitList(mViewModel.getNewestDataList())
        }
    }
}