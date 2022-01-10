package com.mojito.common.holderview

import android.content.Context
import android.view.View
import com.mny.mojito.entension.gone
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.mojito.entension.visible
import com.mny.widget.holderview.HolderLayout
import com.mny.widget.holderview.IHolderLayout
import com.mojito.common.R
import dagger.hilt.android.qualifiers.ActivityContext
import java.lang.ref.WeakReference
import javax.inject.Inject

class HolderViewHelper @Inject constructor(@ActivityContext val context: Context) {

    private lateinit var mHolder: IHolderLayout

    private var mEmptyAndRetryView: View? = null

    private var mLoadingView: View? = null

    private var mRetryWithErrView: View? = null

    fun initOriginView(view: View) {
        mHolder = HolderLayout(WeakReference(view))
    }

    fun showEmpty() {
        if (mEmptyAndRetryView == null) {
            mEmptyAndRetryView = mHolder.inflate(context, R.layout.common_layout_empty)
        }
        val btnRetry = mEmptyAndRetryView?.findViewById<View>(R.id.btn_retry)
        btnRetry?.gone()
        btnRetry?.setOnClickListener(null)
        val emptyView = mEmptyAndRetryView!!
        mHolder.showLayout(emptyView)
    }

    fun showLoading() {

    }

    fun showRetry(onRetryClicked: (() -> Unit)? = null) {
        if (mEmptyAndRetryView == null) {
            mEmptyAndRetryView = mHolder.inflate(context, R.layout.common_layout_empty)
        }
        val btnRetry = mEmptyAndRetryView?.findViewById<View>(R.id.btn_retry)
        btnRetry?.visible()
        btnRetry?.setOnDebouncedClickListener { onRetryClicked?.invoke() }
        val retryView = mEmptyAndRetryView!!
        mHolder.showLayout(retryView)
    }

    fun showOrigin() {
        mHolder.showOriginLayout()
    }


}