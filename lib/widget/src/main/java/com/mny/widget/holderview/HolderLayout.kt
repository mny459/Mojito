package com.mny.widget.holderview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mny.widget.R
import java.lang.ref.WeakReference

/**
 * 用于切换布局,用一个新的布局替换掉原先的布局
 */
class HolderLayout(val mOriginView: WeakReference<View>) : IHolderLayout {
    private var mParentView: ViewGroup? = null
    private var mViewIndex = 0
    private lateinit var mLayoutParams: ViewGroup.LayoutParams
    private lateinit var mCurrentView: View

    override fun showOriginLayout() {
        val view = mOriginView.get()
        if (view != null) {
            showLayout(view)
        }
    }

    override fun currentLayout(): View = mCurrentView

    override fun showLayout(view: View) {
        if (mParentView == null) {
            init()
        }
        mCurrentView = view
        // 如果已经是那个view，那就不需要再进行替换操作了
        mParentView?.apply {
            if (getChildAt(mViewIndex) !== view) {
                val parent = view.parent as? ViewGroup
                parent?.removeView(view)
                removeViewAt(mViewIndex)
                addView(view, mViewIndex, mLayoutParams)
            }
        }
    }

    override fun showLayout(context: Context, layoutId: Int) {
        showLayout(inflate(context, layoutId))
    }

    override fun inflate(context: Context, layoutId: Int): View =
        LayoutInflater.from(context).inflate(layoutId, null)

    private fun init() {
        val view = mOriginView.get() ?: return
        mLayoutParams = view.layoutParams
        val parentView = if (view.parent != null) view.parent as ViewGroup
        else view.rootView.findViewById<View>(R.id.content) as ViewGroup
        val count = parentView.childCount
        for (index in 0 until count) {
            if (mOriginView === parentView.getChildAt(index)) {
                mViewIndex = index
                break
            }
        }
        mParentView = parentView
    }
}