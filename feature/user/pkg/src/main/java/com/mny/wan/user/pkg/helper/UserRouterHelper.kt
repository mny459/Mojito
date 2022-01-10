package com.mny.wan.user.pkg.helper

import android.content.Intent
import com.blankj.utilcode.util.ActivityUtils
import com.mny.wan.user.pkg.presentation.about.AboutActivity
import com.mny.wan.user.pkg.presentation.coin.CoinDetailActivity
import com.mny.wan.user.pkg.presentation.coin.CoinDetailActivity.Companion.ARG_COIN_DETAIL
import com.mny.wan.user.pkg.presentation.login.LoginActivity
import com.mny.wan.user.pkg.presentation.mine.MineActivity
import com.mny.wan.user.pkg.presentation.setting.SettingsActivity

/**
 * UserRouterHelper
 * @author caicai
 * Created on 2021-10-23 15:50
 * Desc:
 */
object UserRouterHelper {
    fun goLogin() {
        ActivityUtils.startActivity(LoginActivity::class.java)
    }

    fun goMine() {
        ActivityUtils.startActivity(MineActivity::class.java)
    }

    fun goSettings() {
        ActivityUtils.startActivity(SettingsActivity::class.java)
    }

    fun goAbout() {
        ActivityUtils.startActivity(AboutActivity::class.java)
    }

    /**
     * [coinDetail] true 个人积分详情
     * false 积分排行榜
     */
    fun goCoinDetail(coinDetail: Boolean = true) {
        val intent = Intent(ActivityUtils.getTopActivity(), CoinDetailActivity::class.java)
        intent.putExtra(ARG_COIN_DETAIL, coinDetail)
        ActivityUtils.startActivity(intent)
    }
}