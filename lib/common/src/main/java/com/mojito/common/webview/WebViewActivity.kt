package com.mojito.common.webview

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.blankj.utilcode.util.LogUtils
import com.mojito.base.BaseToolbarActivity
import com.mny.mojito.entension.observe
import com.mojito.common.R
import com.mojito.common.databinding.ActivityWebViewBinding
import com.mojito.common.helper.SettingHelper
import com.mojito.common.ktx.enterFullScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val KEY_URL = "url"

@AndroidEntryPoint
class WebViewActivity : BaseToolbarActivity<ActivityWebViewBinding>() {

    private val mViewModel: WebViewViewModel by viewModels()

    private var mUrl = ""

    @Inject
    lateinit var mSettingHelper: SettingHelper

    override fun initWindow(savedInstanceState: Bundle?) {
        super.initWindow(savedInstanceState)
        enterFullScreen()
    }

    override fun initArgs(bundle: Bundle?, savedInstanceState: Bundle?): Boolean {
        mUrl = bundle?.getString(KEY_URL) ?: mUrl
        return mUrl.isNotEmpty()
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initToolbar(mBinding.toolbar, "")
        mViewModel.initUrl(mUrl)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val useX5 = mSettingHelper.isUseX5()
        LogUtils.d("是否使用X5 $useX5")
        navController.setGraph(
            if (useX5) R.navigation.x5_web_nav_graph
            else R.navigation.web_nav_graph
        )
    }

    override fun initObserver() {
        super.initObserver()
        observe(mViewModel.stateLiveData) {
            when (it) {
                is WebViewViewModel.ViewState.Title -> mBinding.toolbar.title = it.title
                else -> {
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        mUrl = intent?.getStringExtra(KEY_URL) ?: mUrl
        LogUtils.d("mUrl = $mUrl")
    }

}
