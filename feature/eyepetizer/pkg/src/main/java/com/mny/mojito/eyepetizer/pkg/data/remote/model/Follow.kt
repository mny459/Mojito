package com.mny.mojito.eyepetizer.pkg.data.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Follow(
    @SerializedName("followed")
    val followed: Boolean = false,
    @SerializedName("itemId")
    val itemId: Int = 0,
    @SerializedName("itemType")
    val itemType: String = ""
): Serializable