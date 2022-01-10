package com.mny.wan.pkg.presentation.adapter.tag

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.wan.pkg.R
import com.mojito.common.data.remote.model.*

/**
 * @Author CaiRj
 * @Date 2019/10/18 17:10
 * @Desc
 */
class TagChildProvider : BaseItemProvider<BeanMultiType>() {
    private var mOnChildClicked: ((data: Any) -> Unit)? = null

    override val itemViewType: Int = BeanMultiType.TYPE_CHILD
    override val layoutId: Int = R.layout.cell_tag_child

    override fun convert(helper: BaseViewHolder, item: BeanMultiType) {
        item.data.apply {
            when (this) {
                is BeanSystemChildren -> helper.setText(R.id.tvTagChild, name)
                is BeanArticle -> helper.setText(R.id.tvTagChild, title)
                is BeanHotKey -> helper.setText(R.id.tvTagChild, name)
                else -> {

                }
            }
            helper.itemView.setOnDebouncedClickListener {
                mOnChildClicked?.invoke(item.data)
            }
        }
    }

    fun setOnChildClickListener(onChildClickListener: ((data: Any) -> Unit)) {
        this.mOnChildClicked = onChildClickListener
    }
}