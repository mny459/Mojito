package com.mny.mojito.eyepetizer.pkg.data.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Author(
    @SerializedName("adTrack")
    val adTrack: Any = Any(),
    @SerializedName("approvedNotReadyVideoCount")
    val approvedNotReadyVideoCount: Int = 0,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("expert")
    val expert: Boolean = false,
    @SerializedName("follow")
    val follow: Follow = Follow(),
    @SerializedName("icon")
    val icon: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("ifPgc")
    val ifPgc: Boolean = false,
    @SerializedName("latestReleaseTime")
    val latestReleaseTime: Long = 0,
    @SerializedName("link")
    val link: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("recSort")
    val recSort: Int = 0,
    @SerializedName("shield")
    val shield: Shield = Shield(),
    @SerializedName("videoNum")
    val videoNum: Int = 0
) : Serializable