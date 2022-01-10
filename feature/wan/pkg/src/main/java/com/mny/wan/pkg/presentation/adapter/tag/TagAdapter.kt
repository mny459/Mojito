package com.mny.wan.pkg.presentation.adapter.tag

import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.mojito.common.data.remote.model.*

class TagAdapter(data: MutableList<BeanMultiType>, onChildClickListener: ((data: Any) -> Unit)) :
    BaseProviderMultiAdapter<BeanMultiType>(data) {

    init {
        val childProvider = TagChildProvider()
        childProvider.setOnChildClickListener(onChildClickListener)
        addItemProvider(TagParentProvider())
        addItemProvider(childProvider)
    }

    override fun getItemType(data: List<BeanMultiType>, position: Int): Int = data[position].type

}