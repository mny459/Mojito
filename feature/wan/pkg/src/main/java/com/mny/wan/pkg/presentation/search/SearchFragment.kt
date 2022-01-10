package com.mny.wan.pkg.presentation.search

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.mny.mojito.base.BaseVBFragment
import com.mny.mojito.entension.observeWithViewLifecycle
import com.mny.wan.pkg.R
import com.mojito.common.data.remote.model.*
import com.mny.wan.pkg.databinding.FragmentSearchBinding
import com.mny.wan.pkg.extension.collectOnLifecycle
import com.mny.wan.pkg.presentation.adapter.tag.TagAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseVBFragment<FragmentSearchBinding>() {

    private val mViewModel: SearchViewModel by activityViewModels()

    override fun initView(view: View) {
        super.initView(view)
        val layoutManager = FlexboxLayoutManager(mActivity)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        mBinding.rvSearch.layoutManager = layoutManager
    }

    override fun initViewObserver(viewLifecycleOwner: LifecycleOwner) {
        super.initViewObserver(viewLifecycleOwner)
        collectOnLifecycle(mViewModel.hotKey) { hotKeys ->
            initHotKeys(hotKeys)
        }
    }

    private fun initHotKeys(hotKeys: List<BeanHotKey>) {
        val tags = mutableListOf<BeanMultiType>()
        val hotSearch =
            BeanMultiType(StringUtils.getString(R.string.hot_search), BeanMultiType.TYPE_PARENT)
        tags.add(hotSearch)
        hotKeys.forEach {
            tags.add(BeanMultiType(it, BeanMultiType.TYPE_CHILD))
        }
        val adapter = TagAdapter(tags) {
            if (it is BeanHotKey) {
                LogUtils.d("$it")
                mViewModel.search(it.name)
            }
        }
        mBinding.rvSearch.adapter = adapter
    }

    override fun onFirstInit() {
        super.onFirstInit()
        mViewModel.fetchHotKey()
    }
}