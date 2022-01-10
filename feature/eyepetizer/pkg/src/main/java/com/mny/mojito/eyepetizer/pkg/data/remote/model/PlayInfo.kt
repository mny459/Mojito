package com.mny.mojito.eyepetizer.pkg.data.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PlayInfo(
    @SerializedName("height")
    val height: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("urlList")
    val urlList: List<Url> = listOf(),
    @SerializedName("width")
    val width: Int = 0
)