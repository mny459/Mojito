package com.mny.mojito.widget.pkg.presentation.today

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.mny.mojito.base.BaseVBFragment
import com.mny.mojito.entension.collectOnLifecycle
import com.mny.mojito.widget.pkg.databinding.FragmentMottoPickerBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TodayPickerFragment : BaseVBFragment<FragmentMottoPickerBinding>() {

    private val mViewModel by activityViewModels<TodayPickerViewModel>()

    @Inject
    lateinit var mAdapter: TodayAdapter

    override fun initView(view: View) {
        super.initView(view)
        mBinding.rvMotto.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

        mAdapter.mOnMottoClickedListener = {
            mViewModel.saveMotto(it)
            if (mActivity is TodayConfigurePickerActivity) {
                val resultValue = Intent().apply {
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mViewModel.mWidgetId)
                }
                mActivity?.setResult(Activity.RESULT_OK, resultValue)
            }
            mActivity?.finish()
        }
    }

    override fun onFirstInit() {
        super.onFirstInit()
        mViewModel.initData()
    }

    override fun initOnceObserver(lifecycleOwner: LifecycleOwner) {
        super.initOnceObserver(lifecycleOwner)
        collectOnLifecycle(mViewModel.listModCount) {
            mAdapter.submitList(mViewModel.getNewDataList())
        }
    }
}