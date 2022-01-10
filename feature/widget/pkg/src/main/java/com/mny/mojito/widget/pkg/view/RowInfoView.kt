package com.mny.mojito.widget.pkg.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.mny.mojito.entension.dp
import com.mny.mojito.widget.pkg.R
import com.mny.mojito.widget.pkg.databinding.LayoutInfoRowBinding

/**
 * RowInfoView
 * Desc:
 */
class RowInfoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    val mBinding: LayoutInfoRowBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_info_row, this, true)
        mBinding = LayoutInfoRowBinding.bind(view)
    }

    fun setTitle(title: String) {
        mBinding.tvTitle.text = title
    }

    fun setInfo(info: String) {
        mBinding.tvInfo.text = info
    }

    fun getTitle() = mBinding.tvTitle.text.toString()
    fun getInfo() = mBinding.tvInfo.text.toString()

}