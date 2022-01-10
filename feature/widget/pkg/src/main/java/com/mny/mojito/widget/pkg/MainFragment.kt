package com.mny.mojito.widget.pkg

import android.util.TypedValue
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fondesa.recyclerviewdivider.addDivider
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.mny.mojito.base.BaseVBFragment
import com.mny.mojito.widget.pkg.adapter.WidgetEntryAdapter
import com.mny.mojito.widget.pkg.databinding.FragmentMainBinding
import com.mny.mojito.widget.pkg.model.WidgetModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseVBFragment<FragmentMainBinding>() {

    @Inject
    lateinit var mAdapter: WidgetEntryAdapter

    override fun initView(view: View) {
        super.initView(view)
        mBinding.rvWidgetEntry.apply {
            layoutManager = GridLayoutManager(mActivity, 2)
            adapter = mAdapter
            context.dividerBuilder()
                .size(16, TypedValue.COMPLEX_UNIT_DIP)
                .showSideDividers()
                .asSpace()
                .build()
                .addTo(this)
        }
        mAdapter.apply {
            submitList(WidgetModel.widgets)
            mOnWidgetEntryClicked = {
                when (it) {
                    WidgetModel.launcher -> {
                        findNavController().navigate(MainFragmentDirections.actionToLauncherPreviewFragment())
                    }
                    WidgetModel.live -> {
                    }
                    WidgetModel.motto -> {
                        findNavController().navigate(MainFragmentDirections.actionToMottoFragment())
                    }
                    WidgetModel.timer -> {
                        findNavController().navigate(MainFragmentDirections.actionToTimerNoteFragment())
                    }
                    WidgetModel.today -> {
                        findNavController().navigate(MainFragmentDirections.actionToTodayFragment())
                    }
                }
            }
        }
    }
}