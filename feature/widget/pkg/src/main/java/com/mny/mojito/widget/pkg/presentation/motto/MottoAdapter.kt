package com.mny.mojito.widget.pkg.presentation.motto

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.mojito.widget.pkg.databinding.CellMottoBinding
import com.mny.mojito.widget.pkg.model.Motto
import javax.inject.Inject

/**
 * MottoAdapter
 * Desc:
 */
class MottoAdapter @Inject constructor() : ListAdapter<Motto, MottoAdapter.ViewHolder>(COMPARATOR) {

    var mOnMottoClickedListener: ((motto: Motto) -> Unit)? = null

    companion object {
        private val PAYLOAD_SCORE = Any()
        val COMPARATOR = object : DiffUtil.ItemCallback<Motto>() {
            override fun areContentsTheSame(oldItem: Motto, newItem: Motto): Boolean =
                oldItem.content == newItem.content

            override fun areItemsTheSame(oldItem: Motto, newItem: Motto): Boolean =
                oldItem.id == newItem.id
        }
    }

    class ViewHolder(val binding: CellMottoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(CellMottoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.tvMotto.text = item.content
        holder.binding.root.setOnDebouncedClickListener {
            mOnMottoClickedListener?.invoke(item)
        }
    }
}