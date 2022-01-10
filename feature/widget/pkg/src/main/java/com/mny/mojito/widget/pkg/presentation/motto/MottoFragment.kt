package com.mny.mojito.widget.pkg.presentation.motto

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.base.BaseVBFragment
import com.mny.mojito.entension.collectOnLifecycle
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.mojito.widget.pkg.databinding.FragmentMottoBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MottoFragment : BaseVBFragment<FragmentMottoBinding>() {

    private val mViewModel by activityViewModels<MottoViewModel>()

    @Inject
    lateinit var mAdapter: MottoAdapter

    override fun initView(view: View) {
        super.initView(view)
        mBinding.rvMotto.apply {
            adapter = mAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        mBinding.fabAddMotto.setOnDebouncedClickListener {
            mViewModel.setEditMotto(null)
            findNavController().navigate(MottoFragmentDirections.actionToMottoAddFragment())
        }

        mAdapter.mOnMottoClickedListener = {
            mViewModel.setEditMotto(it)
            findNavController().navigate(MottoFragmentDirections.actionToMottoAddFragment())
        }
    }

    override fun onFirstInit() {
        super.onFirstInit()
        mViewModel.initMottos()
    }

    override fun initOnceObserver(lifecycleOwner: LifecycleOwner) {
        super.initOnceObserver(lifecycleOwner)
        collectOnLifecycle(mViewModel.mottoModCount) {
            LogUtils.d("Motto modeCount = $it")
            mAdapter.submitList(mViewModel.mMottos.toList())
        }
    }
}