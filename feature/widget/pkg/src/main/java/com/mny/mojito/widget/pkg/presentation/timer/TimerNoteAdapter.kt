package com.mny.mojito.widget.pkg.presentation.timer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.mojito.widget.pkg.databinding.CellTimerNoteBinding
import com.mny.mojito.widget.pkg.model.*
import javax.inject.Inject

/**
 * MottoAdapter
 * Desc:
 */
class TimerNoteAdapter @Inject constructor() :
    ListAdapter<TimerNote, TimerNoteAdapter.ViewHolder>(COMPARATOR) {

    var mOnMottoClickedListener: ((note: TimerNote) -> Unit)? = null

    companion object {
        private val PAYLOAD_SCORE = Any()
        val COMPARATOR = object : DiffUtil.ItemCallback<TimerNote>() {
            override fun areContentsTheSame(oldItem: TimerNote, newItem: TimerNote): Boolean =
                oldItem.name == newItem.name

            override fun areItemsTheSame(oldItem: TimerNote, newItem: TimerNote): Boolean =
                oldItem.id == newItem.id
        }
    }

    class ViewHolder(val binding: CellTimerNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(CellTimerNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.tvDesc.text = item.desc
        holder.binding.tvName.text = item.name
        holder.binding.tvDayCountOrProgress.text = item.dayCountOrProgress
        holder.binding.root.setOnDebouncedClickListener {
            mOnMottoClickedListener?.invoke(item)
        }
    }
}