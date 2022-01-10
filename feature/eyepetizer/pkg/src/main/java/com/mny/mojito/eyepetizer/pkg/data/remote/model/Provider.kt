package com.mny.mojito.eyepetizer.pkg.data.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Provider(
    @SerializedName("alias")
    val alias: String = "",
    @SerializedName("icon")
    val icon: String = "",
    @SerializedName("name")
    val name: String = ""
)