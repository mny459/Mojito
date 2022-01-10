package com.mny.widget.holderview

import android.content.Context
import android.view.View

interface IHolderLayout {
    fun currentLayout(): View
    fun showLayout(view: View)
    fun showLayout(context: Context,layoutId: Int)
    fun showOriginLayout()
    fun inflate(context: Context, layoutId: Int): View
}