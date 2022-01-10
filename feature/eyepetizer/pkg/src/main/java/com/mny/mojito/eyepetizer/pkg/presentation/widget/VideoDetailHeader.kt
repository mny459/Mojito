package com.mny.mojito.eyepetizer.pkg.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import coil.load
import com.mny.mojito.entension.gone
import com.mny.mojito.entension.inflate
import com.mny.mojito.entension.showToast
import com.mny.mojito.entension.visible
import com.mny.mojito.eyepetizer.pkg.R
import com.mny.mojito.eyepetizer.pkg.presentation.NewDetailActivity
import com.mny.mojito.eyepetizer.pkg.presentation.adapter.DailyAdapter

/**
 * VideoDetailHeader
 * Desc:
 */
class VideoDetailHeader @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val tvTitle: TextView
    val tvCategory: TextView
    val ivFoldText: ImageView
    val tvDescription: TextView
    val ivCollectionCount: ImageView
    val tvCollectionCount: TextView
    val ivShare: ImageView
    val tvShareCount: TextView
    val ivCache: ImageView
    val tvCache: TextView
    val ivFavorites: ImageView
    val tvFavorites: TextView
    val ivAvatar: ImageView
    val tvAuthorDescription: TextView
    val tvAuthorName: TextView
    val tvFollow: TextView
    val groupAuthor: Group

    init {
        val view = R.layout.item_new_detail_custom_header_type.inflate(this, true)
        tvTitle = view.findViewById(R.id.tvTitle)
        tvCategory = view.findViewById(R.id.tvCategory)
        ivFoldText = view.findViewById(R.id.ivFoldText)
        tvDescription = view.findViewById(R.id.tvDescription)
        ivCollectionCount = view.findViewById(R.id.ivCollectionCount)
        tvCollectionCount = view.findViewById(R.id.tvCollectionCount)
        ivShare = view.findViewById(R.id.ivShare)
        tvShareCount = view.findViewById(R.id.tvShareCount)
        ivCache = view.findViewById(R.id.ivCache)
        tvCache = view.findViewById(R.id.tvCache)
        ivFavorites = view.findViewById(R.id.ivFavorites)
        tvFavorites = view.findViewById(R.id.tvFavorites)
        ivAvatar = view.findViewById(R.id.ivAvatar)
        tvAuthorDescription = view.findViewById(R.id.tvAuthorDescription)
        tvAuthorName = view.findViewById(R.id.tvAuthorName)
        tvFollow = view.findViewById(R.id.tvFollow)
        groupAuthor = view.findViewById(R.id.groupAuthor)
    }

    fun bindVideoInfo(videoInfoData: NewDetailActivity.VideoInfo) {
        videoInfoData?.let {

            groupAuthor.gone()
            tvTitle.text = videoInfoData?.title
            tvCategory.text =
                if (videoInfoData?.library == DailyAdapter.DAILY_LIBRARY_TYPE) "#${videoInfoData?.category} / 开眼精选" else "#${videoInfoData?.category}"
            tvDescription.text = videoInfoData?.description
            tvCollectionCount.text = videoInfoData?.consumption?.collectionCount.toString()
            tvShareCount.text = videoInfoData?.consumption?.shareCount.toString()
            videoInfoData?.author?.run {
                groupAuthor.visible()
                ivAvatar.load(videoInfoData?.author?.icon ?: "")
                tvAuthorDescription.text = videoInfoData?.author?.description
                tvAuthorName.text = videoInfoData?.author?.name
            }
            com.mny.mojito.eyepetizer.pkg.extension.setOnClickListener(
                ivCollectionCount,
                tvCollectionCount,
                ivShare,
                tvShareCount,
                ivCache,
                tvCache,
                ivFavorites,
                tvFavorites,
                tvFollow
            ) {
                when (this) {
                    tvFollow, ivCollectionCount, tvCollectionCount, ivFavorites, tvFavorites -> "TODO".showToast()
                    ivShare, tvShareCount -> "ShareTODO".showToast()
                    ivCache, tvCache -> R.string.currently_not_supported.showToast()
                    else -> {
                    }
                }
            }
        }
    }
}