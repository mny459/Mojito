package com.mny.wan.pkg.base

import android.os.SystemClock

/**
 * FlowData
 * Desc:
 */
abstract class FlowData {
    val updateTime: Long = SystemClock.elapsedRealtime()
}