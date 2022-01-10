package com.mny.mojito.eyepetizer.pkg.data.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Content(
    @SerializedName("adIndex")
    val adIndex: Int = 0,
    @SerializedName("data")
    val contentData: ContentData = ContentData(),
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("tag")
    val tag: Any = Any(),
    @SerializedName("trackingData")
    val trackingData: Any = Any(),
    @SerializedName("type")
    val type: String = ""
)