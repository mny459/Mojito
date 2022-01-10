package com.mny.wan.pkg.presentation.adapter.tag

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mny.wan.pkg.R
import com.mojito.common.data.remote.model.*

/**
 * @Author CaiRj
 * @Date 2019/10/18 17:10
 * @Desc
 */
class TagParentProvider : BaseItemProvider<BeanMultiType>() {

    override val layoutId: Int = R.layout.cell_tag_parent
    override val itemViewType: Int = BeanMultiType.TYPE_PARENT

    override fun convert(helper: BaseViewHolder, item: BeanMultiType) {
        item.data.apply {
            when (this) {
                is BeanSystemParent -> helper.setText(R.id.tvTagParent, name)
                is BeanNav -> helper.setText(R.id.tvTagParent, name)
                is String -> helper.setText(R.id.tvTagParent, this)
            }
        }
    }
}