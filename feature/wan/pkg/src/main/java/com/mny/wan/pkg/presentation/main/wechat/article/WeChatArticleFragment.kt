package com.mny.wan.pkg.presentation.main.wechat.article

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mny.wan.pkg.base.BaseArticleFragment
import com.mny.wan.pkg.base.BaseArticlePageVM
import com.mny.wan.pkg.databinding.FragmentWeChatArticleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeChatArticleFragment : BaseArticleFragment<FragmentWeChatArticleBinding>() {

    private var mWeChatId: Int = 0
    private val mViewModel: WeChatArticleViewModel by viewModels()

    override fun articleViewModel(): BaseArticlePageVM = mViewModel

    override fun initArgs(bundle: Bundle?) {
        super.initArgs(bundle)
        bundle?.let {
            mWeChatId = it.getInt(ARG_WE_CHAT_ID, 0)
        }
    }

    override fun initView(view: View) {
        super.initView(view)
        initRefreshAndRv(mBinding.refresh, mBinding.rvArticles)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mViewModel.initCId(mWeChatId)
        super.initData(savedInstanceState)
    }

    companion object {
        private const val ARG_WE_CHAT_ID = "we_chat_id"

        @JvmStatic
        fun newInstance(weChatId: Int) =
            WeChatArticleFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_WE_CHAT_ID, weChatId)
                }
            }
    }
}