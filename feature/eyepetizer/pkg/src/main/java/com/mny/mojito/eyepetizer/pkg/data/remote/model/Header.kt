package com.mny.mojito.eyepetizer.pkg.data.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Header(
    @SerializedName("actionUrl")
    val actionUrl: String = "",
    @SerializedName("cover")
    val cover: Any = Any(),
    @SerializedName("description")
    val description: String = "",
    @SerializedName("followType")
    val followType: String = "",
    @SerializedName("font")
    val font: Any = Any(),
    @SerializedName("icon")
    val icon: String = "",
    @SerializedName("iconType")
    val iconType: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("issuerName")
    val issuerName: String = "",
    @SerializedName("label")
    val label: Any = Any(),
    @SerializedName("labelList")
    val labelList: Any = Any(),
    @SerializedName("rightText")
    val rightText: Any = Any(),
    @SerializedName("showHateVideo")
    val showHateVideo: Boolean = false,
    @SerializedName("subTitle")
    val subTitle: Any = Any(),
    @SerializedName("subTitleFont")
    val subTitleFont: Any = Any(),
    @SerializedName("tagId")
    val tagId: Int = 0,
    @SerializedName("tagName")
    val tagName: Any = Any(),
    @SerializedName("textAlign")
    val textAlign: String = "",
    @SerializedName("time")
    val time: Long = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("topShow")
    val topShow: Boolean = false
)