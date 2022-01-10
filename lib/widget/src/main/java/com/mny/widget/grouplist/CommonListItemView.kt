package com.mny.widget.grouplist

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.mny.widget.R
import com.mny.widget.databinding.CommonListItemBinding
import com.mny.widget.ktx.ViewHelper

/**
 * CommonListItemView
 * Desc:
 */
class CommonListItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    companion object {
        private const val TAG = "CommonListItemView"

        /**
         * 右侧不显示任何东西
         */
        const val ACCESSORY_TYPE_NONE = 0

        /**
         * 右侧显示一个箭头
         */
        const val ACCESSORY_TYPE_CHEVRON = 1

        /**
         * 右侧显示一个开关
         */
        const val ACCESSORY_TYPE_SWITCH = 2

        /**
         * 自定义右侧显示的 View
         */
        const val ACCESSORY_TYPE_CUSTOM = 3
        private const val TIP_SHOW_NOTHING = 0
        private const val TIP_SHOW_RED_POINT = 1
        private const val TIP_SHOW_NEW = 2

        /**
         * detailText 在 title 文字的下方
         */
        const val VERTICAL = 0

        /**
         * detailText 在 item 的右方
         */
        const val HORIZONTAL = 1

        /**
         * TIP 在左边
         */
        const val TIP_POSITION_LEFT = 0

        /**
         * TIP 在右边
         */
        const val TIP_POSITION_RIGHT = 1
    }


    @IntDef(
        ACCESSORY_TYPE_NONE,
        ACCESSORY_TYPE_CHEVRON,
        ACCESSORY_TYPE_SWITCH,
        ACCESSORY_TYPE_CUSTOM
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class GroupListItemAccessoryType

    @IntDef(VERTICAL, HORIZONTAL)
    @Retention(AnnotationRetention.SOURCE)
    annotation class GroupListItemOrientation

    @IntDef(TIP_POSITION_LEFT, TIP_POSITION_RIGHT)
    @Retention(AnnotationRetention.SOURCE)
    annotation class GroupListItemTipPosition

    /**
     * Item 右侧的 View 的类型
     */
    @GroupListItemAccessoryType
    private var mAccessoryType = 0

    /**
     * 控制 detailText 是在 title 文字的下方还是 item 的右方
     */
    private var mOrientation = HORIZONTAL

    /**
     * 控制红点的位置
     */
    @GroupListItemTipPosition
    private var mTipPosition = TIP_POSITION_LEFT

    private var mAccessoryView: ViewGroup? = null
    protected var mTextView: TextView? = null
    protected var mDetailTextView: TextView? = null
    protected var mSwitch: CheckBox? = null
    private var mRedDot: ImageView? = null
    private var mNewTipView: ImageView? = null
    private val mDisableSwitchSelf = false

    private var mTipShown = TIP_SHOW_NOTHING
    val mBinding: CommonListItemBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.common_list_item, this, true)
        mBinding = CommonListItemBinding.bind(view)
        val array: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.GroupListItemView,
            0,
            0
        )

        @GroupListItemOrientation
        val orientation = array.getInt(
            R.styleable.GroupListItemView_group_list_orientation,
            HORIZONTAL
        )

        @GroupListItemAccessoryType
        val accessoryType = array.getInt(
            R.styleable.GroupListItemView_group_list_accessory_type,
            ACCESSORY_TYPE_NONE
        )
        val initTitleColor =
            array.getColor(R.styleable.GroupListItemView_group_list_title_color, 0)
        val initDetailColor =
            array.getColor(R.styleable.GroupListItemView_group_list_detail_color, 0)
        array.recycle()

        mTextView = findViewById<TextView>(R.id.tv_title)
        mRedDot = findViewById<ImageView>(R.id.iv_tips_dot)
        mNewTipView = findViewById<ImageView>(R.id.iv_tips_new)
        mDetailTextView = findViewById<TextView>(R.id.tv_detail)
        Log.d(TAG, "null() called $initTitleColor")
        mBinding.tvTitle.setTextColor(initTitleColor)
        mBinding.tvDetail.setTextColor(initDetailColor)
        mAccessoryView = findViewById<ViewGroup>(R.id.fl_accessory)

        setOrientation(orientation)
        setAccessoryType(accessoryType)
    }


    fun updateImageViewLp(lpConfig: LayoutParamConfig?) {
        if (lpConfig != null) {
            val lp = mBinding.ivIcon.layoutParams as LayoutParams
            mBinding.ivIcon.layoutParams = lpConfig.onConfig(lp)
        }
    }

    fun setImageDrawable(drawable: Drawable?) {
        with(mBinding.ivIcon) {
            visibility = if (drawable == null) {
                GONE
            } else {
                setImageDrawable(drawable)
                VISIBLE
            }
        }
    }

    fun setTipPosition(@GroupListItemTipPosition tipPosition: Int) {
        if (mTipPosition != tipPosition) {
            mTipPosition = tipPosition
            updateLayoutParams()
        }
    }

    fun getText(): CharSequence = mBinding.tvTitle.text

    fun setText(text: CharSequence) {
        with(mBinding.tvTitle) {
            this.text = text
            setTextColor(Color.BLACK)
            visibility = if (text.isEmpty()) GONE
            else VISIBLE
        }
    }

    /**
     * 切换是否显示小红点
     *
     * @param isShow 是否显示小红点
     */
    fun showRedDot(isShow: Boolean) {
        val oldTipShown = mTipShown
        if (isShow) {
            mTipShown = TIP_SHOW_RED_POINT
        } else if (mTipShown == TIP_SHOW_RED_POINT) {
            mTipShown = TIP_SHOW_NOTHING
        }
        if (oldTipShown != mTipShown) {
            updateLayoutParams()
        }
    }

    /**
     * 切换是否显示更新提示
     *
     * @param isShow 是否显示更新提示
     */
    fun showNewTip(isShow: Boolean) {
        val oldTipShown = mTipShown
        if (isShow) {
            mTipShown = TIP_SHOW_NEW
        } else if (mTipShown == TIP_SHOW_NEW) {
            mTipShown = TIP_SHOW_NOTHING
        }
        if (oldTipShown != mTipShown) {
            updateLayoutParams()
        }
    }


    fun getDetailText(): CharSequence = mBinding.tvDetail.text


    fun setDetailText(text: CharSequence) =
        with(mBinding.tvDetail) {
            this.text = text
            visibility = if (text.isEmpty()) GONE
            else VISIBLE
        }

    fun getOrientation(): Int {
        return mOrientation
    }

    fun setOrientation(@GroupListItemOrientation orientation: Int) {
        if (mOrientation == orientation) {
            return
        }
        mOrientation = orientation
        updateLayoutParams()
    }


    private fun updateLayoutParams() {

        mBinding.ivTipsNew.visibility =
            if (mTipShown == TIP_SHOW_NEW) VISIBLE
            else GONE

        mBinding.ivTipsDot.visibility =
            if (mTipShown == TIP_SHOW_RED_POINT) VISIBLE
            else GONE

        val titleLp = mBinding.tvTitle.layoutParams as LayoutParams
        val detailLp = mDetailTextView!!.layoutParams as LayoutParams
        val newTipLp = mNewTipView!!.layoutParams as LayoutParams
        val redDotLp = mRedDot!!.layoutParams as LayoutParams

        if (mOrientation == VERTICAL) {
            mBinding.tvTitle.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                ViewHelper.getAttrDimen(context, R.attr.group_list_item_title_v_text_size).toFloat()
            )
            mDetailTextView!!.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                ViewHelper.getAttrDimen(context, R.attr.group_list_item_detail_v_text_size)
                    .toFloat()
            )

            titleLp.verticalChainStyle = LayoutParams.CHAIN_PACKED
            titleLp.bottomToBottom = LayoutParams.UNSET
            titleLp.bottomToTop = mBinding.tvDetail.id

            detailLp.horizontalChainStyle = LayoutParams.UNSET
            detailLp.verticalChainStyle = LayoutParams.CHAIN_PACKED
            detailLp.leftToLeft = mBinding.tvTitle.id
            detailLp.leftToRight = LayoutParams.UNSET
            detailLp.horizontalBias = 0f
            detailLp.topToTop = LayoutParams.UNSET
            detailLp.topToBottom = mBinding.tvTitle.id
            detailLp.leftMargin = 0
            detailLp.topMargin = ViewHelper.getAttrDimen(
                context, R.attr.group_list_item_detail_v_margin_with_title
            )
            if (mTipShown == TIP_SHOW_NEW) {
                if (mTipPosition == TIP_POSITION_LEFT) {
                    updateTipLeftVerRelatedLayoutParam(mNewTipView!!, newTipLp, titleLp, detailLp)
                } else {
                    updateTipRightVerRelatedLayoutParam(mNewTipView!!, newTipLp, titleLp, detailLp)
                }
            } else if (mTipShown == TIP_SHOW_RED_POINT) {
                if (mTipPosition == TIP_POSITION_LEFT) {
                    updateTipLeftVerRelatedLayoutParam(mRedDot!!, redDotLp, titleLp, detailLp)
                } else {
                    updateTipRightVerRelatedLayoutParam(mRedDot!!, redDotLp, titleLp, detailLp)
                }
            } else {
                val accessoryLeftMargin: Int = ViewHelper.getAttrDimen(
                    context,
                    R.attr.group_list_item_accessory_margin_left
                )
                titleLp.horizontalChainStyle = LayoutParams.UNSET
                titleLp.rightToLeft = mAccessoryView!!.id
                titleLp.rightMargin = accessoryLeftMargin
                titleLp.goneRightMargin = 0
                detailLp.leftToRight = mAccessoryView!!.id
                detailLp.rightMargin = accessoryLeftMargin
                detailLp.goneRightMargin = 0
            }
        } else {
            mBinding.tvTitle.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                ViewHelper.getAttrDimen(context, R.attr.group_list_item_title_h_text_size).toFloat()
            )
            mDetailTextView!!.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                ViewHelper.getAttrDimen(context, R.attr.group_list_item_detail_h_text_size)
                    .toFloat()
            )

            titleLp.verticalChainStyle = LayoutParams.UNSET
            titleLp.bottomToBottom = LayoutParams.PARENT_ID
            titleLp.bottomToTop = LayoutParams.UNSET

            detailLp.verticalChainStyle = LayoutParams.UNSET
            detailLp.leftToLeft = LayoutParams.UNSET
            detailLp.topToTop = LayoutParams.PARENT_ID
            detailLp.topToBottom = LayoutParams.UNSET
            detailLp.topMargin = 0
            detailLp.leftMargin = ViewHelper.getAttrDimen(
                context,
                R.attr.group_list_item_detail_h_margin_with_title
            )
            if (mTipShown == TIP_SHOW_NEW) {
                if (mTipPosition == TIP_POSITION_LEFT) {
                    updateTipLeftHorRelatedLayoutParam(mNewTipView!!, newTipLp, titleLp, detailLp)
                } else {
                    updateTipRightHorRelatedLayoutParam(mNewTipView!!, newTipLp, titleLp, detailLp)
                }
            } else if (mTipShown == TIP_SHOW_RED_POINT) {
                if (mTipPosition == TIP_POSITION_LEFT) {
                    updateTipLeftHorRelatedLayoutParam(mRedDot!!, redDotLp, titleLp, detailLp)
                } else {
                    updateTipRightHorRelatedLayoutParam(mRedDot!!, redDotLp, titleLp, detailLp)
                }
            } else {
                val accessoryLeftMargin: Int = ViewHelper.getAttrDimen(
                    context,
                    R.attr.group_list_item_accessory_margin_left
                )
                titleLp.horizontalChainStyle = LayoutParams.UNSET
                titleLp.rightToLeft = mBinding.tvDetail.id
                titleLp.rightMargin = accessoryLeftMargin
                titleLp.goneRightMargin = 0
                detailLp.leftToRight = mBinding.tvTitle.id
                detailLp.rightToLeft = mAccessoryView!!.id
                detailLp.rightMargin = accessoryLeftMargin
                detailLp.goneRightMargin = 0
            }
        }
    }

    private fun updateTipLeftVerRelatedLayoutParam(
        tipView: View,
        tipLp: LayoutParams,
        titleLp: LayoutParams,
        detailLp: LayoutParams
    ) {
        val titleRightMargin: Int = ViewHelper.getAttrDimen(
            context,
            R.attr.group_list_item_holder_margin_with_title
        )
        val accessoryLeftMargin: Int =
            ViewHelper.getAttrDimen(context, R.attr.group_list_item_accessory_margin_left)
        titleLp.horizontalChainStyle = LayoutParams.CHAIN_PACKED
        titleLp.horizontalBias = 0f
        titleLp.rightToLeft = tipView.id
        titleLp.rightMargin = titleRightMargin
        tipLp.leftToRight = mBinding.tvTitle.id
        tipLp.rightToLeft = mAccessoryView!!.id
        tipLp.rightMargin = accessoryLeftMargin
        tipLp.topToTop = mTextView!!.id
        tipLp.bottomToBottom = mTextView!!.id
        tipLp.goneRightMargin = 0
        detailLp.rightToLeft = mAccessoryView!!.id
        detailLp.rightMargin = accessoryLeftMargin
        detailLp.goneRightMargin = 0
    }

    private fun updateTipRightVerRelatedLayoutParam(
        tipView: View,
        tipLp: LayoutParams,
        titleLp: LayoutParams,
        detailLp: LayoutParams
    ) {
        val accessoryLeftMargin: Int =
            ViewHelper.getAttrDimen(context, R.attr.group_list_item_accessory_margin_left)
        tipLp.leftToRight = LayoutParams.UNSET
        tipLp.rightToLeft = mAccessoryView!!.id
        tipLp.rightMargin = accessoryLeftMargin
        tipLp.goneRightMargin = 0
        tipLp.topToTop = LayoutParams.PARENT_ID
        tipLp.bottomToBottom = LayoutParams.PARENT_ID
        titleLp.horizontalChainStyle = LayoutParams.UNSET
        titleLp.rightToLeft = tipView.id
        titleLp.rightMargin = accessoryLeftMargin
        detailLp.rightToLeft = tipView.id
        detailLp.rightMargin = accessoryLeftMargin
    }

    private fun updateTipLeftHorRelatedLayoutParam(
        tipView: View,
        tipLp: LayoutParams,
        titleLp: LayoutParams,
        detailLp: LayoutParams
    ) {
        val titleRightMargin: Int = ViewHelper.getAttrDimen(
            context,
            R.attr.group_list_item_holder_margin_with_title
        )
        val accessoryLeftMargin: Int =
            ViewHelper.getAttrDimen(context, R.attr.group_list_item_accessory_margin_left)
        titleLp.horizontalChainStyle = LayoutParams.CHAIN_PACKED
        titleLp.horizontalBias = 0f
        titleLp.rightToLeft = tipView.id
        titleLp.rightMargin = titleRightMargin
        tipLp.leftToRight = mTextView!!.id
        tipLp.rightToLeft = mAccessoryView!!.id
        tipLp.rightMargin = accessoryLeftMargin
        tipLp.topToTop = mTextView!!.id
        tipLp.bottomToBottom = mTextView!!.id
        tipLp.goneRightMargin = 0
        detailLp.leftToRight = tipView.id
        detailLp.rightToLeft = mAccessoryView!!.id
        detailLp.rightMargin = accessoryLeftMargin
        detailLp.goneRightMargin = 0
    }

    private fun updateTipRightHorRelatedLayoutParam(
        tipView: View,
        tipLp: LayoutParams,
        titleLp: LayoutParams,
        detailLp: LayoutParams
    ) {
        val accessoryLeftMargin: Int =
            ViewHelper.getAttrDimen(context, R.attr.group_list_item_accessory_margin_left)
        tipLp.leftToRight = LayoutParams.UNSET
        tipLp.rightToLeft = mAccessoryView!!.id
        tipLp.rightMargin = accessoryLeftMargin
        tipLp.goneRightMargin = 0
        tipLp.topToTop = LayoutParams.PARENT_ID
        tipLp.bottomToBottom = LayoutParams.PARENT_ID
        titleLp.horizontalChainStyle = LayoutParams.UNSET
        titleLp.rightToLeft = tipView.id
        titleLp.rightMargin = accessoryLeftMargin
        titleLp.horizontalBias = 0f
        detailLp.leftToRight = mTextView!!.id
        detailLp.rightToLeft = tipView.id
        detailLp.rightMargin = accessoryLeftMargin
    }

    fun getAccessoryType(): Int {
        return mAccessoryType
    }

    /**
     * 设置右侧 View 的类型。
     *
     * @param type 见 [GroupListItemAccessoryType]
     */
    fun setAccessoryType(@GroupListItemAccessoryType type: Int) = with(mBinding.flAccessory) {
        removeAllViews()
        mAccessoryType = type
        when (type) {
            ACCESSORY_TYPE_CHEVRON -> {
                val tempImageView = getAccessoryImageView()
                tempImageView.setImageDrawable(
                    ViewHelper.getAttrDrawable(
                        context,
                        R.attr.group_list_item_chevron
                    )
                )
                addView(tempImageView)
                visibility = VISIBLE
            }
            ACCESSORY_TYPE_SWITCH -> {
                if (mSwitch == null) {
                    mSwitch = AppCompatCheckBox(context).apply {
                        background = null
                        buttonDrawable = ViewHelper.getAttrDrawable(
                            context,
                            R.attr.group_list_item_switch
                        )
                        layoutParams = getAccessoryLayoutParams()
                        if (mDisableSwitchSelf) {
                            isClickable = false
                            isEnabled = false
                        }
                    }
                }
                addView(mSwitch)
                visibility = VISIBLE
            }
            ACCESSORY_TYPE_CUSTOM -> visibility = VISIBLE
            ACCESSORY_TYPE_NONE -> visibility = GONE
        }
        val titleLp = mBinding.tvTitle.layoutParams as LayoutParams
        val detailLp = mBinding.tvDetail.layoutParams as LayoutParams
        if (visibility != GONE) {
            detailLp.goneRightMargin = detailLp.rightMargin
            titleLp.goneRightMargin = titleLp.rightMargin
        } else {
            detailLp.goneRightMargin = 0
            titleLp.goneRightMargin = 0
        }
    }

    private fun getAccessoryLayoutParams(): ViewGroup.LayoutParams? {
        return ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun getAccessoryImageView(): ImageView {
        val resultImageView = AppCompatImageView(context)
        resultImageView.layoutParams = getAccessoryLayoutParams()
        resultImageView.scaleType = ImageView.ScaleType.CENTER
        //        QMUISkinValueBuilder builder = QMUISkinValueBuilder.acquire();
//        builder.tintColor(R.attr.qmui_skin_support_common_list_chevron_color);
//        QMUISkinHelper.setSkinValue(resultImageView, builder);
//        QMUISkinValueBuilder.release(builder);
        return resultImageView
    }

    fun getTextView(): TextView = mBinding.tvTitle

    fun getDetailTextView(): TextView = mBinding.tvDetail

    fun getSwitch(): CheckBox? {
        return mSwitch
    }

    fun getAccessoryContainerView(): ViewGroup = mBinding.flAccessory

    interface LayoutParamConfig {
        fun onConfig(lp: LayoutParams): LayoutParams
    }
}