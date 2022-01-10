package com.mny.mojito.eyepetizer.pkg.data.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Cover(
    @SerializedName("blurred")
    val blurred: String? = String(),
    @SerializedName("detail")
    val detail: String = "",
    @SerializedName("feed")
    val feed: String = "",
    @SerializedName("homepage")
    val homepage: Any = Any(),
    @SerializedName("sharing")
    val sharing: Any = Any()
) : Serializable