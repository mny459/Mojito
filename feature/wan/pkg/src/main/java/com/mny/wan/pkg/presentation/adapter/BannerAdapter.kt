package com.mny.wan.pkg.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mny.wan.pkg.R
import com.mojito.common.data.remote.model.*
import com.mny.wan.pkg.extension.goWeb
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import javax.inject.Inject

/**
 * RV Adapter 的一个 Item
 */
class BannerAdapter @Inject constructor() : RecyclerView.Adapter<BannerViewHolder>() {
    private val mBanners = mutableListOf<BeanBanner>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(mBanners)
    }

    override fun getItemCount(): Int = 1

    fun replaceBanners(newBanners: List<BeanBanner>) {
        mBanners.clear()
        mBanners.addAll(newBanners)
        notifyItemChanged(0)
    }
}

class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view), OnBannerListener<String> {
    private val mBanner: Banner<String, BannerImageAdapter> = view.findViewById(R.id.banner)
    lateinit var mBannerImageAdapter: BannerImageAdapter
    private val mBanners = mutableListOf<BeanBanner>()

    init {
        mBanner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
    }

    fun bind(banners: MutableList<BeanBanner>) {
        mBanners.clear()
        mBanners.addAll(banners)
        val bannerUrls = mBanners.map { it.imagePath }.toList()
        mBannerImageAdapter = BannerImageAdapter(bannerUrls)
        mBannerImageAdapter.setOnBannerListener(this)
        mBanner.apply {
            adapter = mBannerImageAdapter
            indicator = CircleIndicator(context)
            // banner设置方法全部调用完毕时最后调用
            start()
        }
    }

    companion object {
        fun create(parent: ViewGroup): BannerViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.cell_banner, parent, false)
            return BannerViewHolder(view)
        }
    }

    override fun OnBannerClick(data: String?, position: Int) {
        goWeb(mBanners[position].url)
    }

}

class BannerImageAdapter(data: List<String>) :
    BannerAdapter<String, BannerImageAdapter.BannerViewHolder>(data) {

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder? {
        val imageView = ImageView(parent.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder, data: String, position: Int, size: Int) {
        holder.imageView.load(data)
    }

    inner class BannerViewHolder(@NonNull val imageView: ImageView) :
        RecyclerView.ViewHolder(imageView)
}