package com.mojito.common.helper

import android.annotation.SuppressLint
import android.content.Context
import com.blankj.utilcode.util.ActivityUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

/**
 * LoadingHelper
 * @author caicai
 * Created on 2021-11-02 09:59
 * Desc:
 */
object LoadingHelper {
    @SuppressLint("StaticFieldLeak")
    private var mLoadingView: LoadingPopupView? = null

    fun show(context: Context) {
        if (mLoadingView != null) return
        mLoadingView = XPopup.Builder(context)
            .dismissOnBackPressed(false)
            .isLightNavigationBar(true)
            .isViewMode(true)
            .asLoading().apply {
                show()
            }
    }

    fun hide() {
        mLoadingView?.dismiss()
        mLoadingView = null
    }
}

fun showLoading() {
    LoadingHelper.show(ActivityUtils.getTopActivity())
}

fun hideLoading() {
    LoadingHelper.hide()
}

fun showConfirmDialog(
    title: String? = "提示",
    content: String? = null,
    cancelText: String? = "取消",
    confirmText: String? = "确定",
    cancelListener: (() -> Unit)? = null,
    confirmListener: (() -> Unit)? = null,
) {
    XPopup.Builder(ActivityUtils.getTopActivity())
        .isDestroyOnDismiss(true)
        .asConfirm(
            title,
            content,
            cancelText,
            confirmText,
            { confirmListener?.invoke() },
            { cancelListener?.invoke() },
            cancelText == null
        )
        .show()
}