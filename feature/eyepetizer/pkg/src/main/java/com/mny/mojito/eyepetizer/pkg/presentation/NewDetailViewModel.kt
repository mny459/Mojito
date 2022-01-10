package com.mny.mojito.eyepetizer.pkg.presentation

import com.mny.mojito.mvvm.BaseNoStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * NewDetailViewModel
 * Desc:
 */
@HiltViewModel
class NewDetailViewModel @Inject constructor() : BaseNoStateViewModel() {
    var videoInfoData: NewDetailActivity.VideoInfo? = null

    var videoId: Long = 0L
}