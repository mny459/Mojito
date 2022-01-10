package com.mny.mojito.widget.pkg.presentation.launcher

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.mojito.widget.pkg.databinding.CellLauncherPreviewBinding
import com.mny.mojito.widget.pkg.model.Launcher
import javax.inject.Inject

/**
 * LaucnherPreviewAdapter
 * @author caicai
 * Created on 2021-09-09 14:12
 * Desc:
 */
class LauncherPreviewAdapter @Inject constructor() : ListAdapter<Launcher, LauncherPreviewAdapter.ViewHolder>(COMPARATOR) {

    var mOnMottoClickedListener: ((motto: Launcher) -> Unit)? = null

    companion object {
        private val PAYLOAD_SCORE = Any()
        val COMPARATOR = object : DiffUtil.ItemCallback<Launcher>() {
            override fun areContentsTheSame(oldItem: Launcher, newItem: Launcher): Boolean = false
            override fun areItemsTheSame(oldItem: Launcher, newItem: Launcher): Boolean = false
        }
    }

    class ViewHolder(val binding: CellLauncherPreviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(CellLauncherPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.tvLauncherName.text = item.name
        holder.binding.root.setOnDebouncedClickListener {
            mOnMottoClickedListener?.invoke(item)
        }
    }
}