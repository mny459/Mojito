package com.mny.mojito.eyepetizer.pkg.presentation.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.mny.mojito.entension.*
import com.mny.mojito.eyepetizer.pkg.BuildConfig
import com.mny.mojito.eyepetizer.pkg.R
import com.mny.mojito.eyepetizer.pkg.data.remote.model.Item
import com.mny.mojito.eyepetizer.pkg.presentation.NewDetailActivity
import javax.inject.Inject

/**
 * DailyAdapter
 * Desc:
 */
class DailyAdapter @Inject constructor() : ListAdapter<Item, RecyclerView.ViewHolder>(COMPARATOR) {

    companion object {
        const val TAG = "DailyAdapter"
        const val DAILY_LIBRARY_TYPE = "DAILY"
        const val BIND_VIEWHOLDER_TYPE_WARN = "bindViewHolder Type Unprocessed"
        private val PAYLOAD_SCORE = Any()
        val COMPARATOR = object : DiffUtil.ItemCallback<Item>() {
            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean = false
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean = false
            override fun getChangePayload(oldItem: Item, newItem: Item): Any = PAYLOAD_SCORE
        }
    }

    override fun getItemViewType(position: Int): Int =
        RecyclerViewHelp.getItemViewType(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        RecyclerViewHelp.getViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        refreshItem(position, holder)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        refreshItem(position, holder)
    }

    private fun refreshItem(
        position: Int,
        holder: RecyclerView.ViewHolder
    ) {
        val item = getItem(position)
        when (holder) {
            is TextCardViewHeader5ViewHolder -> {
                holder.binding.tvTitle5.text = item.itemData.text
            }
            is TextCardViewHeader7ViewHolder -> {
                holder.tvTitle7.text = item.itemData.text
            }
            is TextCardViewHeader8ViewHolder -> {
                holder.tvTitle8.text = item.itemData.text
            }
            is TextCardViewFooter2ViewHolder -> {
                holder.tvFooterRightText2.text = item.itemData.text
            }
            is TextCardViewFooter3ViewHolder -> {
                holder.tvFooterRightText3.text = item.itemData.text
            }
            is FollowCardViewHolder -> {
                holder.ivVideo.load(item.itemData.content.contentData.cover.feed) {
                    transformations(RoundedCornersTransformation(4.dp().toFloat()))
                }
                holder.ivAvatar.load(item.itemData.header.icon) {
                    transformations(CircleCropTransformation())
                }
                holder.ivAvatarStar.load(R.drawable.ic_star_white_15dp) {
                    transformations(CircleCropTransformation())
                }
                holder.tvVideoDuration.text =
                    item.itemData.content.contentData.duration.conversionVideoDuration()
                holder.tvDescription.text = item.itemData.header.description
                holder.tvTitle.text = item.itemData.header.title
                if (item.itemData.content.contentData.ad) holder.tvLabel.visible() else holder.tvLabel.gone()
                if (item.itemData.content.contentData.library == DAILY_LIBRARY_TYPE) holder.ivChoiceness.visible() else holder.ivChoiceness.gone()
                //                holder.ivShare.setOnClickListener { showDialogShare(fragment.activity, "${item.itemData.content.contentData.title}：${item.itemData.content.contentData.webUrl.raw}") }
                holder.itemView.setOnDebouncedClickListener {
                    item.itemData.content.contentData.run {
                        if (ad || author == null) {
                            NewDetailActivity.start(id)
                        } else {
                            NewDetailActivity.start(
                                NewDetailActivity.VideoInfo(
                                    id,
                                    playUrl,
                                    title,
                                    description,
                                    category,
                                    library,
                                    consumption,
                                    cover,
                                    author,
                                    webUrl
                                )
                            )
                        }
                    }
                }
            }
            is InformationCardFollowCardViewHolder -> {
                holder.ivCover.load(item.itemData.backgroundImage) {
                    transformations(
                        RoundedCornersTransformation(
                            4.dp().toFloat(),
                            4.dp().toFloat()
                        )
                    )
                }
                holder.recyclerView.setHasFixedSize(true)
                if (holder.recyclerView.itemDecorationCount == 0) {
                    holder.recyclerView.addItemDecoration(InformationCardFollowCardItemDecoration())
                }
                holder.recyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
                holder.recyclerView.adapter = InformationCardFollowCardAdapter(
                    item.itemData.actionUrl,
                    item.itemData.titleList
                )

            }
            else -> {
                holder.itemView.gone()
                if (BuildConfig.DEBUG) "${TAG}:${BIND_VIEWHOLDER_TYPE_WARN}\n${holder}".showToast()
            }
        }
    }
}

/**
 * 获取转换后的时间样式。
 *
 * @return 处理后的时间样式，示例：06:50
 */
fun Int.conversionVideoDuration(): String {
    val minute = 1 * 60
    val hour = 60 * minute
    val day = 24 * hour

    return when {
        this < day -> {
            String.format("%02d:%02d", this / minute, this % 60)
        }
        else -> {
            String.format("%02d:%02d:%02d", this / hour, (this % hour) / minute, this % 60)
        }
    }
}

class InformationCardFollowCardItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position
        val count = parent.adapter?.itemCount //item count
        count?.run {
            when (position) {
                0 -> {
                    outRect.top = 18f.dp()
                }
                count.minus(1) -> {
                    outRect.top = 13f.dp()
                    outRect.bottom = 18f.dp()
                }
                else -> {
                    outRect.top = 13f.dp()
                }
            }
        }
    }
}


class InformationCardFollowCardAdapter(val actionUrl: String?, val dataList: List<String>) :
    RecyclerView.Adapter<InformationCardFollowCardAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNews = view.findViewById<TextView>(R.id.tvNews)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InformationCardFollowCardAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_information_card_type_item, parent, false)
        )
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(
        holder: InformationCardFollowCardAdapter.ViewHolder,
        position: Int
    ) {
        val item = dataList[position]
        holder.tvNews.text = item
//        holder.itemView.setOnClickListener { ActionUrlUtil.process(activity, actionUrl) }
    }
}
