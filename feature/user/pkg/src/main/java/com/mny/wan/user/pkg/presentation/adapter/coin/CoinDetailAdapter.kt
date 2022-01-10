package com.mny.wan.user.pkg.presentation.adapter.coin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.TimeUtils
import com.mny.wan.user.pkg.databinding.CellCoinDetailBinding
import com.mojito.common.data.remote.model.*
import java.util.*
import javax.inject.Inject

/**
 * @Author CaiRj
 * @Date 2019/10/17 16:03
 * @Desc PagingDataAdapter: Paging 要求继承的 Adapter
 */
class CoinDetailAdapter @Inject constructor() :
    ListAdapter<BeanCoinOpDetail, CoinDetailViewHolder>(COMPARATOR) {
    companion object {
        private val PAYLOAD_SCORE = Any()
        val COMPARATOR = object : DiffUtil.ItemCallback<BeanCoinOpDetail>() {
            override fun areContentsTheSame(
                oldItem: BeanCoinOpDetail,
                newItem: BeanCoinOpDetail
            ): Boolean =
                // 显示的内容是否一样，主要判断 名字，头像，性别，是否已经关注
                newItem == oldItem || (Objects.equals(newItem.id, oldItem.id));

            override fun areItemsTheSame(
                oldItem: BeanCoinOpDetail,
                newItem: BeanCoinOpDetail
            ): Boolean =

                // 主要关注Id即可
                newItem == oldItem || newItem.id == oldItem.id

            override fun getChangePayload(
                oldItem: BeanCoinOpDetail,
                newItem: BeanCoinOpDetail
            ): Any = PAYLOAD_SCORE
        }
    }

    override fun onBindViewHolder(holder: CoinDetailViewHolder, position: Int) {
        getItem(position)?.apply {
            holder.bind(this)
        }
    }

    override fun onBindViewHolder(
        holder: CoinDetailViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            // 更新收藏
            val item = getItem(position)
//            holder.updateScore(item)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinDetailViewHolder =
        CoinDetailViewHolder.create(parent)
}

class CoinDetailViewHolder(private val mBinding: CellCoinDetailBinding) :
    RecyclerView.ViewHolder(mBinding.root) {

    fun bind(item: BeanCoinOpDetail?) {
        item?.apply {
            mBinding.tvType.text = reason
            mBinding.tvTime.text = TimeUtils.millis2String(date)
            try {
                mBinding.tvCoin.text =
                    desc.subSequence(desc.indexOfLast { it == '：' } + 1, desc.length)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    companion object {
        fun create(parent: ViewGroup): CoinDetailViewHolder {
            val binding =
                CellCoinDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CoinDetailViewHolder(binding)
        }
    }
}
