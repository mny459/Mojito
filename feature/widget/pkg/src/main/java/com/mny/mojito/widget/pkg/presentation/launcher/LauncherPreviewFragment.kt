package com.mny.mojito.widget.pkg.presentation.launcher

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.mny.mojito.base.BaseVBFragment
import com.mny.mojito.entension.collectOnLifecycle
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.mojito.widget.pkg.databinding.FragmentLauncherPreviewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LauncherPreviewFragment : BaseVBFragment<FragmentLauncherPreviewBinding>() {

    private val mViewModel by activityViewModels<LauncherViewModel>()

    @Inject
    lateinit var mAdapter: LauncherPreviewAdapter

    override fun initView(view: View) {
        super.initView(view)
        mBinding.rvLaunchers.apply {
            adapter = mAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        mBinding.fabAddLauncher.setOnDebouncedClickListener {
            findNavController().navigate(LauncherPreviewFragmentDirections.actionToLauncherPickerFragment())
        }

        mAdapter.mOnMottoClickedListener = {
            // findNavController().navigate(LauncherPreviewFragmentDirections.actionToLauncherEditFragment())
            try {
                val launchIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.schema))
                startActivity(launchIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }

        }
    }

    override fun onFirstInit() {
        super.onFirstInit()
        mViewModel.initPreviewLaunchers()
    }

    override fun initOnceObserver(lifecycleOwner: LifecycleOwner) {
        super.initOnceObserver(lifecycleOwner)
        collectOnLifecycle(mViewModel.launcherModCount) {
            mAdapter.submitList(mViewModel.mPreviewLaunchers)
        }
    }
}