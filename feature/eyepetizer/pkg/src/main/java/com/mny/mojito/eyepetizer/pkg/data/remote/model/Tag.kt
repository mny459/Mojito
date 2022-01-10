package com.mny.mojito.eyepetizer.pkg.data.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Tag(
    @SerializedName("actionUrl")
    val actionUrl: String = "",
    @SerializedName("adTrack")
    val adTrack: Any = Any(),
    @SerializedName("bgPicture")
    val bgPicture: String = "",
    @SerializedName("childTagIdList")
    val childTagIdList: Any = Any(),
    @SerializedName("childTagList")
    val childTagList: Any = Any(),
    @SerializedName("communityIndex")
    val communityIndex: Int = 0,
    @SerializedName("desc")
    val desc: String = "",
    @SerializedName("haveReward")
    val haveReward: Boolean = false,
    @SerializedName("headerImage")
    val headerImage: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("ifNewest")
    val ifNewest: Boolean = false,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("newestEndTime")
    val newestEndTime: Any = Any(),
    @SerializedName("tagRecType")
    val tagRecType: String = ""
)