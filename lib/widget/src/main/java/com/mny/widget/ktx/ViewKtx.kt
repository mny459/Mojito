package com.mny.widget.ktx

import android.content.res.Resources

internal fun Float.dp2px(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

internal fun Int.dp2px(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (this * scale + 0.5f).toInt()
}