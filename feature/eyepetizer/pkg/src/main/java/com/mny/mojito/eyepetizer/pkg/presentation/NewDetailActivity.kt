package com.mny.mojito.eyepetizer.pkg.presentation

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import coil.load
import com.blankj.utilcode.util.ActivityUtils
import com.mojito.base.BaseToolbarActivity
import com.mny.mojito.entension.gone
import com.mny.mojito.entension.showToast
import com.mny.mojito.entension.visible
import com.mny.mojito.eyepetizer.pkg.R
import com.mny.mojito.eyepetizer.pkg.data.remote.model.Author
import com.mny.mojito.eyepetizer.pkg.data.remote.model.Consumption
import com.mny.mojito.eyepetizer.pkg.data.remote.model.Cover
import com.mny.mojito.eyepetizer.pkg.data.remote.model.WebUrl
import com.mny.mojito.eyepetizer.pkg.databinding.ActivityNewDetailBinding
import com.mny.mojito.eyepetizer.pkg.extension.goneAlphaAnimation
import com.mny.mojito.eyepetizer.pkg.extension.invisibleAlphaAnimation
import com.mny.mojito.eyepetizer.pkg.extension.visibleAlphaAnimation
import com.shuyu.gsyvideoplayer.GSYVideoADManager
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.io.Serializable

@AndroidEntryPoint
class NewDetailActivity : BaseToolbarActivity<ActivityNewDetailBinding>() {
    private val viewModel by viewModels<NewDetailViewModel>()

    private var orientationUtils: OrientationUtils? = null

    private val globalJob by lazy { Job() }

    private var hideTitleBarJob: Job? = null

    private var hideBottomContainerJob: Job? = null

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initParams()
        orientationUtils = OrientationUtils(this, mBinding.videoPlayer)

        mBinding.refreshLayout.run {
            setDragRate(0.7f)
            setHeaderTriggerRate(0.6f)
            setFooterTriggerRate(0.6f)
            setEnableLoadMoreWhenContentNotFull(true)
            setEnableFooterFollowWhenNoMoreData(true)
            setEnableFooterTranslationContent(true)
            setEnableScrollContentWhenLoaded(true)
            setEnableNestedScroll(true)
            setFooterHeight(153f)
            setOnRefreshListener { finish() }
        }

        startVideoPlayer()
        mBinding.videoDetailHeader.bindVideoInfo(viewModel.videoInfoData!!)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (checkArguments()) {
            initParams()
            startVideoPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        mBinding.videoPlayer.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        mBinding.videoPlayer.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoADManager.releaseAllVideos()
        orientationUtils?.releaseListener()
        mBinding.videoPlayer.release()
        mBinding.videoPlayer.setVideoAllCallBack(null)
        globalJob.cancel()
    }

    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        if (GSYVideoManager.backFromWindowFull(this)) return
        super.onBackPressed()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.anl_push_bottom_out)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mBinding.videoPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
    }

    private fun checkArguments() =
        if (intent.getSerializableExtra(EXTRA_VIDEOINFO) == null && intent.getLongExtra(
                EXTRA_VIDEO_ID,
                0L
            ) == 0L
        ) {
            R.string.jump_page_unknown_error.showToast()
            finish()
            false
        } else {
            true
        }

    private fun initParams() {
        if (intent.getSerializableExtra(EXTRA_VIDEOINFO) != null) viewModel.videoInfoData =
            intent.getSerializableExtra(EXTRA_VIDEOINFO) as VideoInfo
        if (intent.getLongExtra(EXTRA_VIDEO_ID, 0L) != 0L) viewModel.videoId =
            intent.getLongExtra(EXTRA_VIDEO_ID, 0L)
    }

    private fun startVideoPlayer() {
        viewModel.videoInfoData?.run {
            mBinding.ivBlurredBg.load(cover.blurred)
            mBinding.tvReplyCount.text = consumption.replyCount.toString()
            mBinding.videoPlayer.startPlay()
        }
    }

    private fun GSYVideoPlayer.startPlay() {
        viewModel.videoInfoData?.let {
            //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
            fullscreenButton.setOnClickListener { showFull() }
            //防止错位设置
            playTag = TAG
            //音频焦点冲突时是否释放
            isReleaseWhenLossAudio = false
            //增加封面
            val imageView = ImageView(this@NewDetailActivity)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.load(it.cover.detail)
            thumbImageView = imageView
            thumbImageView.setOnClickListener { switchTitleBarVisible() }
            //是否开启自动旋转
            isRotateViewAuto = false
            //是否需要全屏锁定屏幕功能
            isNeedLockFull = true
            //是否可以滑动调整
            setIsTouchWiget(true)
            //设置触摸显示控制ui的消失时间
            dismissControlTime = 5000
            //设置播放过程中的回调
            setVideoAllCallBack(VideoCallPlayBack())
            //设置播放URL
            setUp(it.playUrl, false, it.title)
            //开始播放
            startPlayLogic()
        }
    }

    private fun switchTitleBarVisible() {
        if (mBinding.videoPlayer.currentPlayer.currentState == GSYVideoView.CURRENT_STATE_AUTO_COMPLETE) return
        if (mBinding.flHeader.visibility == View.VISIBLE) {
            hideTitleBar()
        } else {
            mBinding.flHeader.visibleAlphaAnimation(1000)
            mBinding.ivPullDown.visibleAlphaAnimation(1000)
            mBinding.ivCollection.visibleAlphaAnimation(1000)
            mBinding.ivMore.visibleAlphaAnimation(1000)
            mBinding.ivShare.visibleAlphaAnimation(1000)
            delayHideTitleBar()
        }
    }

    private fun hideTitleBar() {
        mBinding.flHeader.invisibleAlphaAnimation(1000)
        mBinding.ivPullDown.goneAlphaAnimation(1000)
        mBinding.ivCollection.goneAlphaAnimation(1000)
        mBinding.ivMore.goneAlphaAnimation(1000)
        mBinding.ivShare.goneAlphaAnimation(1000)
    }

    private fun delayHideTitleBar() {
        hideTitleBarJob?.cancel()
        hideTitleBarJob = CoroutineScope(globalJob).launch(Dispatchers.Main) {
            delay(mBinding.videoPlayer.dismissControlTime.toLong())
            hideTitleBar()
        }
    }

    private fun delayHideBottomContainer() {
        hideBottomContainerJob?.cancel()
        hideBottomContainerJob = CoroutineScope(globalJob).launch(Dispatchers.Main) {
            delay(mBinding.videoPlayer.dismissControlTime.toLong())
            mBinding.videoPlayer.getBottomContainer().gone()
            mBinding.videoPlayer.startButton.gone()
        }
    }

    private fun showFull() {
        orientationUtils?.run { if (isLand != 1) resolveByClick() }
        mBinding.videoPlayer.startWindowFullscreen(this, true, false)
    }


    inner class VideoCallPlayBack : GSYSampleCallBack() {
        override fun onStartPrepared(url: String?, vararg objects: Any?) {
            super.onStartPrepared(url, *objects)
            mBinding.flHeader.gone()
            mBinding.llShares.gone()
        }

        override fun onClickBlank(url: String?, vararg objects: Any?) {
            super.onClickBlank(url, *objects)
            switchTitleBarVisible()
        }

        override fun onClickStop(url: String?, vararg objects: Any?) {
            super.onClickStop(url, *objects)
            delayHideBottomContainer()
        }

        override fun onAutoComplete(url: String?, vararg objects: Any?) {
            super.onAutoComplete(url, *objects)
            mBinding.flHeader.visible()
            mBinding.ivPullDown.visible()
            mBinding.ivCollection.gone()
            mBinding.ivShare.gone()
            mBinding.ivMore.gone()
            mBinding.llShares.visible()
        }
    }

    data class VideoInfo(
        val videoId: Long,
        val playUrl: String,
        val title: String,
        val description: String,
        val category: String,
        val library: String,
        val consumption: Consumption,
        val cover: Cover,
        val author: Author?,
        val webUrl: WebUrl
    ) : Serializable

    companion object {
        const val TAG = "NewDetailActivity"

        const val EXTRA_VIDEOINFO = "videoInfo"
        const val EXTRA_VIDEO_ID = "videoId"

        fun start(videoInfo: VideoInfo) {
            val topActivity = ActivityUtils.getTopActivity()
            val starter = Intent(topActivity, NewDetailActivity::class.java)
            starter.putExtra(EXTRA_VIDEOINFO, videoInfo)
            topActivity.startActivity(starter)
            topActivity.overridePendingTransition(R.anim.anl_push_bottom_in, R.anim.anl_push_up_out)
        }

        fun start(videoId: Long) {
            val topActivity = ActivityUtils.getTopActivity()
            val starter = Intent(topActivity, NewDetailActivity::class.java)
            starter.putExtra(EXTRA_VIDEO_ID, videoId)
            topActivity.startActivity(starter)
            topActivity.overridePendingTransition(R.anim.anl_push_bottom_in, R.anim.anl_push_up_out)
        }
    }
}