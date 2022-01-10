package com.mny.mojito.entension

import com.blankj.utilcode.util.ToastUtils

fun String?.showToast() {
    if (!this.isNullOrEmpty()) {
        ToastUtils.showShort(this)
    }
}

fun String?.showLongToast() {
    if (!this.isNullOrEmpty()) {
        ToastUtils.showLong(this)
    }
}

fun Int.showToast() {
    ToastUtils.showShort(this)
}

fun Int.showLongToast() {
    ToastUtils.showLong(this)
}
