package com.mny.mojito.widget.pkg.presentation.motto

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import com.mny.mojito.base.BaseVBFragment
import com.mny.mojito.entension.collectOnLifecycle
import com.mny.mojito.widget.pkg.databinding.FragmentMottoPickerBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MottoPickerFragment : BaseVBFragment<FragmentMottoPickerBinding>() {

    private val mViewModel by activityViewModels<MottoPickerViewModel>()

    @Inject
    lateinit var mAdapter: MottoAdapter

    override fun initView(view: View) {
        super.initView(view)
        mBinding.rvMotto.apply {
            adapter = mAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        mAdapter.mOnMottoClickedListener = {
            mViewModel.saveMotto(it)
            if (mActivity is MottoConfigurePickerActivity) {
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
        mViewModel.initMottos()
    }

    override fun initOnceObserver(lifecycleOwner: LifecycleOwner) {
        super.initOnceObserver(lifecycleOwner)
        collectOnLifecycle(mViewModel.mottoModCount) {
            mAdapter.submitList(mViewModel.mMottos.toList())
        }
    }
}