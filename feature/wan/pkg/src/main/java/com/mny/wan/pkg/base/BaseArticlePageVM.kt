package com.mny.wan.pkg.base

import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.mvvm.BasePageVM
import com.mojito.common.data.remote.model.BeanArticle
import com.mojito.common.data.remote.model.BeanArticleList

abstract class BaseArticlePageVM : BasePageVM<BeanArticleList>() {

    val mArticles = mutableListOf<BeanArticle>()

    override fun onNewData(data: BeanArticleList) {
        super.onNewData(data)
        LogUtils.d("${this.javaClass.simpleName} Page.VM onNewData $data ")
        if (isRefresh()) {
            mArticles.clear()
        }
        data.articles.apply {
            mArticles.addAll(this)
        }
        // mCurPage = data.curPage
        updateNoMoreData(data.over && data.offset >= data.total)
    }
}