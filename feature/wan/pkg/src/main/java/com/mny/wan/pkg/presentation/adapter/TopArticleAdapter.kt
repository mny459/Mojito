package com.mny.wan.pkg.presentation.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.StringUtils
import com.mny.mojito.entension.gone
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.mojito.entension.showToast
import com.mny.mojito.entension.visible
import com.mny.wan.pkg.extension.loadProjectPreview
import com.mny.wan.pkg.R
import com.mojito.common.data.local.UserHelper
import com.mojito.common.data.remote.model.*
import com.mny.wan.pkg.databinding.CellArticleBinding
import com.mny.wan.pkg.extension.goWeb
import com.mny.wan.pkg.helper.WanRouterHelper
import com.mny.wan.pkg.presentation.AppViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class TopArticleAdapter @Inject constructor(private val mAppViewModel: AppViewModel) :
    ListAdapter<BeanArticle, TopArticleViewHolder>(COMPARATOR) {
    var viewLifecycleOwner: LifecycleOwner? = null

    companion object {
        private val PAYLOAD_SCORE = Any()
        val COMPARATOR = object : DiffUtil.ItemCallback<BeanArticle>() {
            override fun areContentsTheSame(oldItem: BeanArticle, newItem: BeanArticle): Boolean =
                // 显示的内容是否一样，主要判断 名字，头像，性别，是否已经关注
                newItem == oldItem || (Objects.equals(newItem.collect, oldItem.collect));

            override fun areItemsTheSame(oldItem: BeanArticle, newItem: BeanArticle): Boolean =
                // 主要关注Id即可
                newItem == oldItem || newItem.id == oldItem.id

            override fun getChangePayload(oldItem: BeanArticle, newItem: BeanArticle): Any =
                PAYLOAD_SCORE
        }
    }

    override fun onBindViewHolder(holder: TopArticleViewHolder, position: Int) {
        getItem(position)?.apply {
            holder.bind(this)
        }
    }

    override fun onBindViewHolder(
        holder: TopArticleViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        onBindViewHolder(holder, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopArticleViewHolder =
        TopArticleViewHolder.create(parent, mAppViewModel, viewLifecycleOwner!!)

    override fun onViewDetachedFromWindow(holder: TopArticleViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.cancelObserverCollect()
    }
}

class TopArticleViewHolder(
    private val binding: CellArticleBinding,
    val viewModel: AppViewModel,
    private val viewLifecycleOwner: LifecycleOwner
) :
    RecyclerView.ViewHolder(binding.root) {
    private var mCollectJob: Job? = null
    private var mCancelCollectJob: Job? = null
    private var mItem: BeanArticle? = null
    fun bind(item: BeanArticle?) {
        mItem = item
        item?.apply {
            binding.tvTitle.text = Html.fromHtml(title)
            binding.tvPublishTime.text = Html.fromHtml(niceDate)
            binding.tvPin.visible()
            binding.tvPublisher.text = if (author.isEmpty()) shareUser.trim() else author.trim()
            binding.tvUserType.text = StringUtils.getString(
                if (author.isEmpty()) R.string.wan_share_user
                else R.string.wan_author
            )
            binding.tvChapter.text = if (superChapterName.isEmpty()) "$chapterName".trim()
            else "$chapterName·$superChapterName".trim()
            if (superChapterId == 294) {
                // 开源项目主Tab
                binding.ivProject.loadProjectPreview(envelopePic)
                binding.tvDesc.text = Html.fromHtml(desc)
                binding.groupProject.visible()
            } else {
                binding.groupProject.gone()
            }
            binding.root.setOnDebouncedClickListener {
                goWeb(link)
            }
            binding.ivCollect.isSelected = item.collect
            binding.ivCollect.setOnDebouncedClickListener {
                if (!NetworkUtils.isConnected()) {
                    "网络连接失败，请检查网络设置".showToast()
                    return@setOnDebouncedClickListener
                }
                if (!UserHelper.isLogin()) {
                    WanRouterHelper.goLogin()
                    return@setOnDebouncedClickListener
                }
                item.collect = binding.ivCollect.isSelected
            }
            observerCollect(this)
        }

    }

    private fun observerCollect(item: BeanArticle) {
        viewLifecycleOwner.apply {
            mCollectJob = lifecycleScope.launch {
                viewModel.collectIds.collect {
                    // LogUtils.d("收藏的id $it 当前文章 ${item.id}")
                    if (it != 0 && it == item.id) {
                        item.collect = true
                        binding.ivCollect.isSelected = item.collect
                    }
                }
            }
            mCancelCollectJob = lifecycleScope.launch {
                viewModel.cancelCollectIds.collect {
                    // LogUtils.d("取消收藏的id $it 当前文章 ${item.id}")
                    if (it != 0 && it == item.id) {
                        item.collect = false
                        binding.ivCollect.isSelected = item.collect
                    }
                }
            }
        }
    }

    fun cancelObserverCollect() {
        mCollectJob?.cancel()
        mCancelCollectJob?.cancel()
        mCollectJob = null
        mCancelCollectJob = null
    }

    companion object {
        fun create(
            parent: ViewGroup,
            viewModel: AppViewModel,
            viewLifecycleOwner: LifecycleOwner
        ): TopArticleViewHolder {
            val binding =
                CellArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TopArticleViewHolder(binding, viewModel, viewLifecycleOwner)
        }
    }
}