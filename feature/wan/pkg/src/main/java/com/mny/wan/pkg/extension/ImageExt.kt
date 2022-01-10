package com.mny.wan.pkg.extension

import android.widget.ImageView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.mny.mojito.entension.dp

/**
 * @Author CaiRj
 * @Date 2019/10/17 17:10
 * @Desc
 */
fun ImageView.loadProjectPreview(url: String? = null) {
    if (!url.isNullOrEmpty()) {
        load(url) {
            transformations(RoundedCornersTransformation(2.dp().toFloat()),)
        }
    }
}