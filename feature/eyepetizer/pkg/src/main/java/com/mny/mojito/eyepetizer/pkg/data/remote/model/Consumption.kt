package com.mny.mojito.eyepetizer.pkg.data.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Consumption(
    @SerializedName("collectionCount")
    val collectionCount: Int = 0,
    @SerializedName("realCollectionCount")
    val realCollectionCount: Int = 0,
    @SerializedName("replyCount")
    val replyCount: Int = 0,
    @SerializedName("shareCount")
    val shareCount: Int = 0
) : Serializable