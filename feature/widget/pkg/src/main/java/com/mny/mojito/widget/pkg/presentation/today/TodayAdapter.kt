package com.mny.mojito.widget.pkg.presentation.today

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.mojito.widget.pkg.databinding.CellTodayBinding
import com.mny.mojito.widget.pkg.model.Today
import com.mny.mojito.widget.pkg.model.a
import com.mny.mojito.widget.pkg.model.now
import com.mny.mojito.widget.pkg.model.q
import javax.inject.Inject

/**
 * MottoAdapter
 * Desc:
 */
class TodayAdapter @Inject constructor() : ListAdapter<Today, TodayAdapter.ViewHolder>(COMPARATOR) {

    var mOnMottoClickedListener: ((today: Today) -> Unit)? = null

    companion object {
        private val PAYLOAD_SCORE = Any()
        val COMPARATOR = object : DiffUtil.ItemCallback<Today>() {
            override fun areContentsTheSame(oldItem: Today, newItem: Today): Boolean =
                oldItem.label == newItem.label

            override fun areItemsTheSame(oldItem: Today, newItem: Today): Boolean =
                oldItem.id == newItem.id
        }
    }

    class ViewHolder(val binding: CellTodayBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(CellTodayBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.tvDate.text = item.now
        holder.binding.tvTodayQ.text = item.q
        holder.binding.tvTodayA.text = item.a
        holder.binding.root.setOnDebouncedClickListener {
            mOnMottoClickedListener?.invoke(item)
        }
    }
}