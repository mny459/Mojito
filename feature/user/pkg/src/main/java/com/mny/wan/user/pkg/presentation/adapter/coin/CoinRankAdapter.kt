package com.mny.wan.user.pkg.presentation.adapter.coin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mny.wan.user.pkg.databinding.CellCoinRankingBinding
import com.mojito.common.data.remote.model.*
import javax.inject.Inject

/**
 * @Author CaiRj
 * @Date 2019/10/17 16:03
 * @Desc PagingDataAdapter: Paging 要求继承的 Adapter
 */
class CoinRankAdapter @Inject constructor() :
    ListAdapter<BeanRanking, CoinRankViewHolder>(COMPARATOR) {

    companion object {
        private val PAYLOAD_SCORE = Any()
        val COMPARATOR = object : DiffUtil.ItemCallback<BeanRanking>() {
            override fun areContentsTheSame(
                oldItem: BeanRanking,
                newItem: BeanRanking
            ): Boolean =
                // 显示的内容是否一样，主要判断 名字，头像，性别，是否已经关注
                newItem == oldItem || (newItem.coinCount == oldItem.coinCount && newItem.rank == oldItem.rank)

            override fun areItemsTheSame(
                oldItem: BeanRanking,
                newItem: BeanRanking
            ): Boolean =

                // 主要关注Id即可
                newItem == oldItem || newItem.userId == oldItem.userId

            override fun getChangePayload(
                oldItem: BeanRanking,
                newItem: BeanRanking
            ): Any = PAYLOAD_SCORE
        }
    }

    override fun onBindViewHolder(holder: CoinRankViewHolder, position: Int) {
        getItem(position)?.apply {
            holder.bind(this, position)
        }
    }

    override fun onBindViewHolder(
        holder: CoinRankViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        onBindViewHolder(holder, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinRankViewHolder =
        CoinRankViewHolder.create(parent)
}

class CoinRankViewHolder(private val mBinding: CellCoinRankingBinding) :
    RecyclerView.ViewHolder(mBinding.root) {

    fun bind(item: BeanRanking, position: Int) {
        with(mBinding) {
            tvNo.text = "${position + 1}"
            tvName.text = item.username
            tvCoin.text = item.coinCount.toString()
        }
    }

    companion object {
        fun create(parent: ViewGroup): CoinRankViewHolder {
            val binding =
                CellCoinRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CoinRankViewHolder(binding)
        }
    }
}
