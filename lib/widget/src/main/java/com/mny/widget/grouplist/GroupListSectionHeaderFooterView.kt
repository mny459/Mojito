package com.mny.widget.grouplist

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.mny.widget.R
import com.mny.widget.databinding.QmuiGroupListSectionLayoutBinding

/**
 * GroupListSectionHeaderFooterView
 * Desc:
 */
class GroupListSectionHeaderFooterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var mBinding: QmuiGroupListSectionLayoutBinding

    init {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.qmui_group_list_section_layout, this, true)
        mBinding = QmuiGroupListSectionLayoutBinding.bind(view)
        gravity = Gravity.BOTTOM
    }

    constructor(
        context: Context,
        titleText: CharSequence,
        isFooter: Boolean = false,
    ) : this(context, null) {
        if (isFooter) {
            // Footer View 不需要 padding bottom
            setPadding(paddingLeft, paddingTop, paddingRight, 0)
        }
        setText(titleText)
    }

    fun setText(text: CharSequence) {
        with(mBinding.groupListSectionHeaderTextView) {
            visibility = if (text.isNullOrEmpty()) GONE
            else VISIBLE
            this.text = text
        }
    }

    fun getTextView() = mBinding.groupListSectionHeaderTextView

    fun setTextGravity(gravity: Int) {
        mBinding.groupListSectionHeaderTextView.gravity = gravity
    }
}