package com.mny.wan.pkg.presentation.main.system

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.mny.mojito.base.BaseVBFragment
import com.mny.wan.pkg.data.remote.model.*
import com.mny.wan.pkg.databinding.FragmentSystemTagBinding
import com.mny.wan.pkg.extension.collectOnLifecycle
import com.mny.wan.pkg.extension.goWeb
import com.mny.wan.pkg.presentation.adapter.tag.TagAdapter
import com.mny.wan.pkg.presentation.main.system.secondsystem.SystemChildrenActivity
import com.mojito.common.data.remote.model.*
import com.mojito.common.holderview.HolderViewHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SystemTagFragment : BaseVBFragment<FragmentSystemTagBinding>() {

    private var mTag = TAG_SYSTEM
    private val mViewModel by viewModels<SystemTagViewModel>()

    @Inject
    lateinit var mHolderViewHelper: HolderViewHelper

    override fun initArgs(bundle: Bundle?) {
        super.initArgs(bundle)
        mTag = bundle?.getInt(TAG, TAG_SYSTEM) ?: TAG_SYSTEM
    }

    override fun initView(view: View) {
        super.initView(view)
        mHolderViewHelper.initOriginView(mBinding.rvTags)
        val layoutManager = FlexboxLayoutManager(mActivity)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        mBinding.rvTags.layoutManager = layoutManager
    }

    override fun initOnceObserver(lifecycleOwner: LifecycleOwner) {
        super.initOnceObserver(lifecycleOwner)
        when (mTag) {
            TAG_SYSTEM -> collectOnLifecycle(mViewModel.systemTree) { list ->
                if (list.isNullOrEmpty()) {
                    mHolderViewHelper.showRetry { mViewModel.loadData() }
                } else {
                    mHolderViewHelper.showOrigin()
                    refreshSystemData(list)
                }
            }
            TAG_NAV -> collectOnLifecycle(mViewModel.navTree) { list ->
                if (list.isNullOrEmpty()) {
                    mHolderViewHelper.showRetry { mViewModel.loadData() }
                } else {
                    mHolderViewHelper.showOrigin()
                    refreshNavData(list)
                }
            }
            else -> {
            }
        }
    }

    override fun onFirstInit() {
        super.onFirstInit()
        mViewModel.initTag(mTag)
        mViewModel.loadDataWithCache()
        mViewModel.loadData()
    }

    private fun refreshSystemData(list: List<BeanSystemParent>?) {
        val data = mutableListOf<BeanMultiType>()
        list?.forEach { parent ->
            data.add(BeanMultiType(parent, BeanMultiType.TYPE_PARENT))
            parent.children.forEach { child ->
                data.add(BeanMultiType(child, BeanMultiType.TYPE_CHILD))
            }
        }
        val adapter = TagAdapter(data) {
            if (it is BeanSystemChildren) {
                SystemChildrenActivity.show(
                    list?.firstOrNull { parent -> it.parentChapterId == parent.id },
                    it.id
                )
            }
        }
        mBinding.rvTags.adapter = adapter
    }

    private fun refreshNavData(list: List<BeanNav>?) {
        val data = mutableListOf<BeanMultiType>()
        list?.forEach { parent ->
            data.add(BeanMultiType(parent, BeanMultiType.TYPE_PARENT))
            parent.articles.forEach { child ->
                data.add(BeanMultiType(child, BeanMultiType.TYPE_CHILD))
            }
        }
        val adapter = TagAdapter(data) {
            if (it is BeanArticle) {
                goWeb(it.link)
            }
        }
        mBinding.rvTags.adapter = adapter
    }

    companion object {
        const val TAG = "tag"
        const val TAG_SYSTEM = 0
        const val TAG_NAV = 1
        fun newInstance(tag: Int): SystemTagFragment = SystemTagFragment()
            .apply {
                arguments = Bundle().apply {
                    putInt(TAG, tag)
                }
            }
    }
}