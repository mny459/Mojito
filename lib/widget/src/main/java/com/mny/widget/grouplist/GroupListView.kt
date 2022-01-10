package com.mny.widget.grouplist

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.mny.widget.ktx.dp2px

/**
 * GroupListView
 * Desc:
 */
class GroupListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val mSections by lazy { SparseArray<Section>() }

    init {
        orientation = VERTICAL
    }

    companion object {
        private const val TAG = "GroupListView"
        fun newSection(context: Context): Section = Section(context)
    }

    fun createItemView(
        imageDrawable: Drawable? = null,
        titleText: CharSequence? = null,
        detailText: String? = null,
        orientation: Int = CommonListItemView.HORIZONTAL,
        accessoryType: Int = CommonListItemView.ACCESSORY_TYPE_NONE,
        height: Int = 56.dp2px(),
    ): CommonListItemView {
        val itemView = CommonListItemView(context)
        itemView.setImageDrawable(imageDrawable)
        itemView.setText(titleText ?: "")
        itemView.setDetailText(detailText ?: "")
        itemView.setOrientation(orientation)
        itemView.setAccessoryType(accessoryType)
        itemView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, height)
        return itemView
    }

    fun getSectionCount(): Int = mSections.size()


    /**
     * private, use [Section.addTo]
     *
     * 这里只是把section记录到数组里面而已
     */
    fun addSection(section: Section) {
        mSections.append(mSections.size(), section)
    }

    /**
     * private，use [Section.removeFrom]
     *
     * 这里只是把section从记录的数组里移除而已
     */
    fun removeSection(section: Section) {
        for (i in 0 until mSections.size()) {
            val each: Section = mSections.valueAt(i)
            if (each === section) {
                mSections.remove(i)
            }
        }
    }

    fun getSection(index: Int): Section? {
        return mSections[index]
    }


    class Section(private val context: Context) {
        private val mItemViews = SparseArray<CommonListItemView>()
        private var mTitleView: GroupListSectionHeaderFooterView? = null
        private var mDescriptionView: GroupListSectionHeaderFooterView? = null
        private var mUseDefaultTitleIfNone = false

        private var mUseTitleViewForSectionSpace = true
        private var mSeparatorColorAttr: Int =
            0 // R.attr.qmui_skin_support_common_list_separator_color
        private var mHandleSeparatorCustom = false
        private var mShowSeparator = true
        private var mOnlyShowStartEndSeparator = false
        private var mOnlyShowMiddleSeparator = false
        private var mMiddleSeparatorInsetLeft = 0
        private var mMiddleSeparatorInsetRight = 0

        private var mBgAttr: Int = 0 // R.attr.qmui_skin_support_s_common_list_bg

        private var mLeftIconWidth = ViewGroup.LayoutParams.WRAP_CONTENT
        private var mLeftIconHeight = ViewGroup.LayoutParams.WRAP_CONTENT

        /**
         * 对 Section 添加一个 [QMUICommonListItemView]
         *
         * @param itemView            要添加的 ItemView
         * @param onClickListener     ItemView 的点击事件
         * @param onLongClickListener ItemView 的长按事件
         * @return Section 本身, 支持链式调用
         */
        fun addItemView(
            itemView: CommonListItemView,
            onClickListener: OnClickListener? = null,
            onLongClickListener: OnLongClickListener? = null
        ) = apply {
            if (onClickListener != null) {
                itemView.setOnClickListener(onClickListener)
            }
            if (onLongClickListener != null) {
                itemView.setOnLongClickListener(onLongClickListener)
            }
            mItemViews.append(mItemViews.size(), itemView)
        }

        /**
         * 设置 Section 的 title
         *
         * @return Section 本身, 支持链式调用
         */
        fun setTitle(title: CharSequence) = apply {
            mTitleView = createSectionHeader(title)
        }

        /**
         * 设置 Section 的 description
         *
         * @return Section 本身, 支持链式调用
         */
        fun setDescription(description: CharSequence) = apply {
            mDescriptionView = createSectionFooter(description)
        }

        fun setUseDefaultTitleIfNone(useDefaultTitleIfNone: Boolean) = apply {
            mUseDefaultTitleIfNone = useDefaultTitleIfNone
        }

        fun setUseTitleViewForSectionSpace(useTitleViewForSectionSpace: Boolean) = apply {
            mUseTitleViewForSectionSpace = useTitleViewForSectionSpace

        }

        fun setLeftIconSize(width: Int, height: Int) = apply {
            mLeftIconHeight = height
            mLeftIconWidth = width
        }

        fun setSeparatorColorAttr(attr: Int) = apply {
            mSeparatorColorAttr = attr

        }

        fun setHandleSeparatorCustom(handleSeparatorCustom: Boolean) = apply {
            mHandleSeparatorCustom = handleSeparatorCustom

        }

        fun setShowSeparator(showSeparator: Boolean) = apply {
            mShowSeparator = showSeparator

        }

        fun setOnlyShowStartEndSeparator(onlyShowStartEndSeparator: Boolean) = apply {
            mOnlyShowStartEndSeparator = onlyShowStartEndSeparator

        }

        fun setOnlyShowMiddleSeparator(onlyShowMiddleSeparator: Boolean) = apply {
            mOnlyShowMiddleSeparator = onlyShowMiddleSeparator

        }

        fun setMiddleSeparatorInset(insetLeft: Int, insetRight: Int) = apply {
            mMiddleSeparatorInsetLeft = insetLeft
            mMiddleSeparatorInsetRight = insetRight
        }

        fun setBgAttr(bgAttr: Int) = apply {
            mBgAttr = bgAttr

        }


        /**
         * 将 Section 添加到 [QMUIGroupListView] 上
         */
        fun addTo(groupListView: GroupListView) {
            if (mTitleView == null) {
                if (mUseDefaultTitleIfNone) {
                    setTitle("Section " + groupListView.getSectionCount())
                } else if (mUseTitleViewForSectionSpace) {
                    setTitle("")
                }
            }
            if (mTitleView != null) {
                groupListView.addView(mTitleView)
            }
            val itemViewCount: Int = mItemViews.size()
            val leftIconLpConfig =
                object : CommonListItemView.LayoutParamConfig {
                    override fun onConfig(lp: ConstraintLayout.LayoutParams): ConstraintLayout.LayoutParams {
                        lp.width = mLeftIconWidth
                        lp.height = mLeftIconHeight
                        return lp
                    }
                }
//            val separatorColor =
//                QMUIResHelper.getAttrColor(groupListView.context, mSeparatorColorAttr);
//            Log.d(TAG, "addTo() called with: groupListView = $itemViewCount")
            for (i in 0 until itemViewCount) {
                val itemView = mItemViews[i]
//                if (!amHandleSeparatorCustom && mShowSeparator) {
//                    if (itemViewCount == 1) {
//                        itemView.updateTopDivider(0, 0, 1, separatorColor);
//                        itemView.updateBottomDivider(0, 0, 1, separatorColor);
//                    } else if (i == 0) {
//                        if (!mOnlyShowMiddleSeparator) {
//                            itemView.updateTopDivider(0, 0, 1, separatorColor);
//                        }
//                        if (!mOnlyShowStartEndSeparator) {
//                            itemView.updateBottomDivider(
//                                mMiddleSeparatorInsetLeft,
//                                mMiddleSeparatorInsetRight,
//                                1,
//                                separatorColor
//                            );
//                        }
//                    } else if (i == itemViewCount - 1) {
//                        if (!mOnlyShowMiddleSeparator) {
//                            itemView.updateBottomDivider(0, 0, 1, separatorColor);
//                        }
//                    } else if (!mOnlyShowStartEndSeparator) {
//                        itemView.updateBottomDivider(
//                            mMiddleSeparatorInsetLeft,
//                            mMiddleSeparatorInsetRight,
//                            1,
//                            separatorColor
//                        );
//                    }
//                }
                itemView.updateImageViewLp(leftIconLpConfig)
                groupListView.addView(itemView)
            }

            if (mDescriptionView != null) {
                groupListView.addView(mDescriptionView)
            }
            groupListView.addSection(this)
        }

        fun removeFrom(parent: GroupListView) {
            if (mTitleView != null && mTitleView!!.parent === parent) {
                parent.removeView(mTitleView)
            }
            for (i in 0 until mItemViews.size()) {
                val itemView: CommonListItemView = mItemViews.get(i)
                parent.removeView(itemView)
            }
            if (mDescriptionView != null && mDescriptionView!!.parent === parent) {
                parent.removeView(mDescriptionView)
            }
            parent.removeSection(this)
        }

        /**
         * 创建 Section Header，每个 Section 都会被创建一个 Header，有 title 时会显示 title，没有 title 时会利用 header 的上下 padding 充当 Section 分隔条
         */
        fun createSectionHeader(titleText: CharSequence): GroupListSectionHeaderFooterView {
            return GroupListSectionHeaderFooterView(context, titleText)
        }

        /**
         * Section 的 Footer，形式与 Header 相似，都是显示一段文字
         */
        fun createSectionFooter(text: CharSequence): GroupListSectionHeaderFooterView {
            return GroupListSectionHeaderFooterView(context, text, true)
        }

    }
}