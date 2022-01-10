package com.mny.wan.pkg.presentation.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.mny.mojito.entension.gone
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.mojito.entension.showToast
import com.mny.mojito.entension.visible
import com.mojito.common.data.local.UserHelper
import com.mojito.common.data.remote.model.*
import com.mny.wan.pkg.databinding.CellArticleBinding
import com.mny.wan.pkg.extension.goWeb
import com.mny.wan.pkg.extension.loadProjectPreview
import com.mny.wan.pkg.helper.WanRouterHelper
import com.mny.wan.pkg.presentation.AppViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * @Author CaiRj
 * @Date 2019/10/17 16:03
 * @Desc
 */
class ArticleAdapter @Inject constructor(private val mAppViewModel: AppViewModel) :
    ListAdapter<BeanArticle, ArticleViewHolder>(COMPARATOR) {
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

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        getItem(position)?.apply {
            holder.bind(this)
        }
    }

    override fun onBindViewHolder(
        holder: ArticleViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        onBindViewHolder(holder, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder.create(parent, mAppViewModel, viewLifecycleOwner!!)

    override fun onViewDetachedFromWindow(holder: ArticleViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.cancelObserverCollect()
    }
}

class ArticleViewHolder(
    private val mBinding: CellArticleBinding,
    val viewModel: AppViewModel,
    private val viewLifecycleOwner: LifecycleOwner
) : RecyclerView.ViewHolder(mBinding.root) {

    private var mCollectJob: Job? = null
    private var mCancelCollectJob: Job? = null
    private var mItem: BeanArticle? = null
    fun bind(item: BeanArticle?) {
        mItem = item
        item?.apply {
            mBinding.tvTitle.text = Html.fromHtml(title)
            mBinding.tvPublishTime.text = Html.fromHtml(niceDate)
            mBinding.tvPublisher.text = if (author.isEmpty()) shareUser.trim() else author.trim()
            mBinding.tvUserType.text = if (author.isEmpty()) "分享人" else "作者"
            mBinding.tvChapter.text = if (superChapterName.isEmpty()) "$chapterName".trim()
            else "$chapterName·$superChapterName".trim()
            if (superChapterId == 294) {
                // 开源项目主Tab
                mBinding.ivProject.loadProjectPreview(envelopePic)
                mBinding.tvDesc.text = Html.fromHtml(desc)
                mBinding.groupProject.visible()
            } else {
                mBinding.groupProject.gone()
            }
            mBinding.root.setOnDebouncedClickListener {
                LogUtils.d("$item")
                goWeb(link)
            }
            mBinding.ivCollect.isSelected = item.collect
            mBinding.ivCollect.setOnDebouncedClickListener {
                if (!NetworkUtils.isConnected()) {
                    "网络连接失败，请检查网络设置".showToast()
                    return@setOnDebouncedClickListener
                }
                if (!UserHelper.isLogin()) {
                    WanRouterHelper.goLogin()
                    return@setOnDebouncedClickListener
                }
                if (item.collect) {
                    viewModel.cancelCollect(id)
                } else {
                    viewModel.collect(id)
                }
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
                        mBinding.ivCollect.isSelected = item.collect
                    }
                }
            }
            mCancelCollectJob = lifecycleScope.launch {
                viewModel.cancelCollectIds.collect {
                    // LogUtils.d("取消收藏的id $it 当前文章 ${item.id}")
                    if (it != 0 && it == item.id) {
                        item.collect = false
                        mBinding.ivCollect.isSelected = item.collect
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
        ): ArticleViewHolder {
            val binding =
                CellArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ArticleViewHolder(binding, viewModel, viewLifecycleOwner)
        }
    }
}
