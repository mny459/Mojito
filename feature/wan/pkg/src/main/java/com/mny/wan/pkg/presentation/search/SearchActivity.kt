package com.mny.wan.pkg.presentation.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.LogUtils
import com.mojito.base.BaseToolbarActivity
import com.mny.wan.pkg.R
import com.mny.wan.pkg.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchActivity : BaseToolbarActivity<ActivitySearchBinding>() {

    private val mViewModel: SearchViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.imgBack.setOnClickListener { onBackPressed() }
        mBinding.etContent.addTextChangedListener {
            val text = it?.toString()?.trimStart()?.trimEnd() ?: ""
            search(text)
        }

        mBinding.imgSearch.setOnClickListener {
            val text = mBinding.etContent.text.toString().trimStart().trimEnd()
            search(text)
        }
    }

    override fun initObserver() {
        super.initObserver()
        mViewModel.observerSearchKeys()
        lifecycleScope.launchWhenResumed {
            mViewModel.searchKey.collectLatest {
                LogUtils.d("搜索内容是否为空 ${it.isEmpty()} $it")
                val navController = findNavController(R.id.nav_host_fragment)
                if (it.isEmpty()) {
                    navController.navigateUp()
                } else {
                    mBinding.etContent.apply {
                       setText(it)
                       setSelection(it.length)
                    }
                    navController.navigate(
                        R.id.searchResultFragment,
                        null,
                        NavOptions.Builder().setLaunchSingleTop(true).build()
                    )
                }
            }
        }
    }

    private fun search(query: String) {
        mViewModel.search(query)
    }

    override fun onResume() {
        super.onResume()
        KeyboardUtils.showSoftInput(mBinding.etContent)
    }

    override fun onPause() {
        super.onPause()
        KeyboardUtils.hideSoftInput(mBinding.etContent)
    }

}