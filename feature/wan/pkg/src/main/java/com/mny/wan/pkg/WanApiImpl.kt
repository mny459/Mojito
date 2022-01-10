package com.mny.wan.pkg

import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ApiUtils
import com.mny.mojito.wan.export.WanApi
import com.mny.wan.pkg.presentation.collect.CollectActivity
import com.mny.wan.pkg.presentation.main.home.HomeFragment
import com.mny.wan.pkg.presentation.main.project.ProjectActivity
import com.mny.wan.pkg.presentation.main.qa.QAFragment
import com.mny.wan.pkg.presentation.main.system.SystemFragment
import com.mny.wan.pkg.presentation.main.system.share.ShareActivity
import com.mny.wan.pkg.presentation.main.wechat.WeChatActivity

@ApiUtils.Api
class WanApiImpl : WanApi() {
    override fun getHomeFragment(): Fragment = HomeFragment.newInstance()

    override fun getQAFragment(): Fragment = QAFragment.newInstance()

    override fun getSystemFragment(): Fragment = SystemFragment.newInstance()

    override fun goCollectArticle() {
        ActivityUtils.startActivity(CollectActivity::class.java)
    }

    override fun goProject() {
        ActivityUtils.startActivity(ProjectActivity::class.java)
    }

    override fun goWeChat() {
        ActivityUtils.startActivity(WeChatActivity::class.java)
    }

    override fun goShare() {
        ActivityUtils.startActivity(ShareActivity::class.java)
    }
}