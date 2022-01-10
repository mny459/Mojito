package com.mny.wan.user.pkg.presentation.login

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.ActivityUtils
import com.mny.mojito.base.BaseVBFragment
import com.mny.mojito.entension.collectOnLifecycle
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.mojito.entension.showToast
import com.mny.mojito.status.UIStatus
import com.mny.wan.user.pkg.R
import com.mny.wan.user.pkg.databinding.LoginFragmentBinding
import com.mny.wan.user.pkg.helper.UserRouterHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseVBFragment<LoginFragmentBinding>() {

    private val mViewModel: LoginViewModel by viewModels()

    override fun initView(view: View) {
        mBinding.toolbar.setNavigationOnClickListener {
            if (ActivityUtils.getActivityList().size > 1) {
                mActivity?.onBackPressed()
            }
        }
        mBinding.btnLogin.setOnDebouncedClickListener {
            mViewModel.login(
                mBinding.etName.text.toString().trim(),
                mBinding.etPassword.text.toString().trim()
            )
        }
        mBinding.btnRegister.setOnDebouncedClickListener {
            findNavController().navigate(R.id.action_to_register)
        }
    }

    override fun initOnceObserver(lifecycleOwner: LifecycleOwner) {
        super.initOnceObserver(lifecycleOwner)
        collectOnLifecycle(mViewModel.loginState) {
            when (it) {
                is UIStatus.UIData -> {
                    R.string.login_success.showToast()
                    mActivity?.finish()
                    if (ActivityUtils.getActivityList().size == 1) {
                        UserRouterHelper.goMine()
                    }
                }
                is UIStatus.UIError -> it.errMsg.showToast()
                else -> {
                }
            }
        }
    }
}
