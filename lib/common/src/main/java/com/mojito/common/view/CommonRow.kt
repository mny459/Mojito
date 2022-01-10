package com.mojito.common.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CompoundButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.mny.mojito.entension.gone
import com.mojito.common.R
import com.mojito.common.databinding.CellCommonRowBinding


class CommonRowView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    companion object {
        private val PADDING = SizeUtils.dp2px(16F)
        private val WIDTH = ScreenUtils.getScreenWidth()
        private val HEIGHT = SizeUtils.dp2px(56F)
    }

    private var mSubtitle: CharSequence?
    private var mSubtitleOn: CharSequence?
    private var mSubtitleOff: CharSequence?
    private var mShowSwitch: Boolean = false
    private val mBinding: CellCommonRowBinding
    private var mOnCheckedChangeListener: CompoundButton.OnCheckedChangeListener? = null

    init {
        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonRowView)
        val title = a.getText(R.styleable.CommonRowView_title)
        mSubtitle = a.getText(R.styleable.CommonRowView_subtitle)
        mSubtitleOn = a.getText(R.styleable.CommonRowView_subtitleOn)
        mSubtitleOff = a.getText(R.styleable.CommonRowView_subtitleOff)
        mShowSwitch = a.getBoolean(R.styleable.CommonRowView_showSwitch, false)
        a.recycle()
        val view = LayoutInflater.from(context).inflate(R.layout.cell_common_row, this)
        mBinding = CellCommonRowBinding.bind(view)
        mBinding.tvTitle.text = title
        if (mSubtitle.isNullOrEmpty() && mSubtitleOn.isNullOrEmpty() && mSubtitleOff.isNullOrEmpty()) {
            mBinding.tvSubtitle.gone()
        }
        updateSubtitle()
        mBinding.switchRowSwitch.isClickable = false
        mBinding.switchRowSwitch.isFocusable = false
    }

    private fun updateSubtitle() {
        if (!mShowSwitch) {
            mBinding.switchRowSwitch.gone()
            return
        }
        mBinding.tvSubtitle.text = getSubtitleText(mBinding.switchRowSwitch.isChecked)
    }

    private fun getSubtitleText(isChecked: Boolean): CharSequence {
        return if (isChecked && mSubtitleOn != null) {
            mSubtitleOn!!
        } else if (!isChecked && mSubtitleOff != null) {
            mSubtitleOff!!
        } else {
            mSubtitle ?: ""
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        mBinding.switchRowSwitch.isEnabled = enabled
        mBinding.tvTitle.isEnabled = enabled
        mBinding.tvSubtitle.isEnabled = enabled
    }

    fun setChecked(checked: Boolean, log: Boolean = false) {
        if (isChecked() == checked) return
//        if (log) {
//            LogUtils.d("CommonRowView setChecked $this setChecked $checked ${mSwitch.isChecked}")
//        }
        mBinding.switchRowSwitch.isChecked = checked
        updateSubtitle()
    }

    fun isChecked() = mBinding.switchRowSwitch.isChecked
    fun switchChecked(): Boolean {
        setChecked(!isChecked(), false)
        return isChecked()
    }
}