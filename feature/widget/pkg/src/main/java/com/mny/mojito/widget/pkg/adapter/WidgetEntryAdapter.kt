package com.mny.mojito.widget.pkg.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.mojito.widget.pkg.databinding.CellWidgetEntryBinding
import com.mny.mojito.widget.pkg.model.WidgetModel
import javax.inject.Inject

typealias OnWidgetEntryClicked = (entry: WidgetModel) -> Unit

class WidgetEntryAdapter @Inject constructor() :
    ListAdapter<WidgetModel, WidgetEntryAdapter.ViewHolder>(diff) {

    var mOnWidgetEntryClicked: OnWidgetEntryClicked? = null

    companion object {
        val diff = object : DiffUtil.ItemCallback<WidgetModel>() {
            override fun areItemsTheSame(
                oldItem: WidgetModel,
                newItem: WidgetModel
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: WidgetModel,
                newItem: WidgetModel
            ): Boolean = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        CellWidgetEntryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            tvTitle.text = getItem(position).name
            ivIcon.setImageResource(getItem(position).img)
            clContainer.setBackgroundColor(getItem(position).mainColor)
            clContainer.setOnDebouncedClickListener {
                mOnWidgetEntryClicked?.invoke(getItem(position))
            }
        }
    }


    inner class ViewHolder(val binding: CellWidgetEntryBinding) :
        RecyclerView.ViewHolder(binding.root)
}