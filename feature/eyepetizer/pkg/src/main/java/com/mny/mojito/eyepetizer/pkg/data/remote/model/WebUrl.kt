package com.mny.mojito.eyepetizer.pkg.data.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class WebUrl(
    @SerializedName("forWeibo")
    val forWeibo: String = "",
    @SerializedName("raw")
    val raw: String = ""
) : Serializable