package com.mny.mojito.entension

import android.os.SystemClock
import android.text.InputFilter
import android.view.View
import android.widget.EditText

/**
 * Desc:
 */
fun View.setOnDebouncedClickListener(action: () -> Unit) {
    val actionDebouncer = ActionDebouncer(action, 1000)

    // This is the only place in the project where we should actually use setOnClickListener
    setOnClickListener {
        actionDebouncer.notifyAction()
    }
}

fun View.removeOnDebouncedClickListener() {
    setOnClickListener(null)
}

private class ActionDebouncer(private val action: () -> Unit, private val intervalTime: Long?) {

    companion object {
        const val DEBOUNCE_INTERVAL_MILLISECONDS = 600L
    }

    private var lastActionTime = 0L

    fun notifyAction() {
        val now = SystemClock.elapsedRealtime()

        val millisecondsPassed = now - lastActionTime
        val actionAllowed = millisecondsPassed > (intervalTime ?: DEBOUNCE_INTERVAL_MILLISECONDS)
        lastActionTime = now

        if (actionAllowed) {
            action.invoke()
        }
    }
}
/**
 * 隐藏View
 */
fun View.gone() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

/**
 * 显示View
 * @receiver View
 */
fun View.visible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

/**
 * View不可见但存在原位置
 */
fun View.invisible() {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
}

fun View.visibleOrGone(visible: Boolean) {
    if (visible) visible()
    else gone()
}

fun View.visibleOrInvisible(visible: Boolean) {
    if (visible) visible()
    else invisible()
}

/**
 * 判断View是不是[View.VISIBLE]状态
 */
val View.isVisible: Boolean
    get() {
        return visibility == View.VISIBLE
    }

/**
 * 判断View是不是[View.INVISIBLE]状态
 */
val View.isInvisible: Boolean
    get() {
        return visibility == View.INVISIBLE
    }

/**
 * 判断View是不是[View.GONE]状态
 */
val View.isGone: Boolean
    get() {
        return visibility == View.GONE
    }
