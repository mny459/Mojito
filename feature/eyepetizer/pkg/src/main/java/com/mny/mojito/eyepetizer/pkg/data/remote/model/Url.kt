package com.mny.mojito.eyepetizer.pkg.data.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Url(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("size")
    val size: Int = 0,
    @SerializedName("url")
    val url: String = ""
)