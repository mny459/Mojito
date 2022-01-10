package com.mny.mojito.widget.pkg.presentation.motto

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.lxj.xpopup.XPopup
import com.mny.mojito.base.BaseVBFragment
import com.mny.mojito.entension.collectOnLifecycle
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.mojito.widget.pkg.databinding.FragmentMottoAddBinding
import com.mny.mojito.widget.pkg.model.Motto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MottoAddFragment : BaseVBFragment<FragmentMottoAddBinding>() {

    private val mViewModel by activityViewModels<MottoViewModel>()

    override fun initView(view: View) {
        super.initView(view)
        mBinding.tvMotto.setOnDebouncedClickListener { showMottoEditPopup() }
        mBinding.rivContent.setTitle("内容")
        mBinding.rivContent.setOnDebouncedClickListener { showMottoEditPopup() }

        mBinding.tvSave.setOnDebouncedClickListener {
            mViewModel.saveMotto()
            findNavController().popBackStack()
        }

        mBinding.tvDelete.setOnDebouncedClickListener {
            mViewModel.deleteMotto()
            findNavController().popBackStack()
        }
    }

    override fun initOnceObserver(lifecycleOwner: LifecycleOwner) {
        super.initOnceObserver(lifecycleOwner)
        collectOnLifecycle(mViewModel.editMotto) { motto: Motto? ->
            val content = motto?.content ?: "请输入要展示的内容"
            mBinding.tvMotto.text = content
            mBinding.rivContent.setInfo(content)
        }
    }

    private fun showMottoEditPopup() {
        if (mActivity != null && !(mActivity!!.isDestroyed || mActivity!!.isFinishing)) {
            XPopup.Builder(context)
                .hasStatusBarShadow(true) //
                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                .autoOpenSoftInput(true)
                .asCustom(MottoEditView(mActivity!!, mViewModel))
                .show()
        }
    }
}