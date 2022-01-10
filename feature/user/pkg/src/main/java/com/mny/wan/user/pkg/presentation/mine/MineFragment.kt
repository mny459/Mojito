package com.mny.wan.user.pkg.presentation.mine

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.ApiUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.StringUtils
import com.gyf.immersionbar.ImmersionBar
import com.lxj.xpopup.XPopup
import com.mny.mojito.entension.collectOnLifecycle
import com.mny.mojito.entension.visibleOrInvisible
import com.mny.mojito.wan.export.WanApi
import com.mny.wan.user.pkg.R
import com.mny.wan.user.pkg.databinding.FragmentMineBinding
import com.mny.wan.user.pkg.helper.UserRouterHelper
import com.mojito.base.BaseImmersionVBFragment
import com.mojito.common.data.local.UserHelper
import com.mojito.common.helper.showConfirmDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MineFragment : BaseImmersionVBFragment<FragmentMineBinding>() {

    private val mMineViewModel by viewModels<MineViewModel>()

    override fun initView(view: View) {
        super.initView(view)
        mBinding.ivLogout.setOnClickListener {
            showConfirmDialog(title = null, content = "确定退出登录？") {
                mMineViewModel.logout()
            }
        }
        mBinding.tvLoginOrNickname.setOnClickListener {
            if (!UserHelper.isLogin()) {
                UserRouterHelper.goLogin()
            }
        }
        mBinding.rowCoinCount.setOnClickListener {
            if (UserHelper.isLogin()) {
                UserRouterHelper.goCoinDetail()
            } else {
                UserRouterHelper.goLogin()
            }
        }
        mBinding.rowCollect.setOnClickListener {
            if (!UserHelper.isLogin()) {
                UserRouterHelper.goLogin()
            } else {
                ApiUtils.getApi(WanApi::class.java)?.goCollectArticle()
            }
        }
        mBinding.rowProject.setOnClickListener {
            ApiUtils.getApi(WanApi::class.java)?.goProject()
        }
        mBinding.rowWeChatArticle.setOnClickListener {
            ApiUtils.getApi(WanApi::class.java)?.goWeChat()
        }
        mBinding.rowSquare.setOnClickListener {
            ApiUtils.getApi(WanApi::class.java)?.goShare()
        }
        mBinding.rowSettings.setOnClickListener {
            UserRouterHelper.goSettings()
        }
        mBinding.refresh.setOnRefreshListener {
            if (NetworkUtils.isConnected()) {
                mMineViewModel.updateUserInfo()
            } else {
                it.finishRefresh()
            }
        }
    }

    override fun initOnceObserver(lifecycleOwner: LifecycleOwner) {
        super.initOnceObserver(lifecycleOwner)
        collectOnLifecycle(mMineViewModel.userInfo) {
            LogUtils.d("Mine.initViewObserver $it")
            mBinding.refresh.finishRefresh()
            mBinding.groupUserInfo.visibleOrInvisible(it.isLogin)
            mBinding.refresh.setEnableRefresh(it.isLogin)
            if (it.isLogin) {
                mBinding.tvLoginOrNickname.text = it.nickname
                mBinding.tvCredits.text = "等级: ${it.level} 排名: ${it.rank}"
            } else {
                mBinding.tvLoginOrNickname.text =
                    StringUtils.getString(R.string.mine_click_to_login)
            }
        }
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(false)
            .autoNavigationBarDarkModeEnable(true)
            .init()
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mMineViewModel.updateUserInfo()
    }


    companion object {
        @JvmStatic
        fun newInstance() = MineFragment()
    }
}