package com.mny.wan.user.pkg.presentation.coin

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.mny.wan.user.pkg.R
import com.mny.wan.user.pkg.databinding.ActivityCoinDetailBinding
import com.mojito.base.BaseToolbarActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailActivity : BaseToolbarActivity<ActivityCoinDetailBinding>() {

    private var mCoinDetail = true

    override fun initArgs(bundle: Bundle?, savedInstanceState: Bundle?): Boolean {
        mCoinDetail = bundle?.getBoolean(ARG_COIN_DETAIL, mCoinDetail) ?: mCoinDetail
        return super.initArgs(bundle, savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(
            if (mCoinDetail) R.navigation.coin_detail_nav_graph
            else R.navigation.coin_rank_nav_graph
        )
    }

    companion object{
        const val ARG_COIN_DETAIL = "coinDetail"
    }

}