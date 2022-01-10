package com.mny.mojito.entension

import com.blankj.utilcode.util.SizeUtils

/**
 * UIExt
 * Desc:
 */

fun Int.dp(): Int = SizeUtils.dp2px(this.toFloat())

fun Float.dp(): Int = SizeUtils.dp2px(this)