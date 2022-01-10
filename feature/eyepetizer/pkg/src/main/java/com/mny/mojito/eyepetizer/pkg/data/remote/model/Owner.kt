package com.mny.mojito.eyepetizer.pkg.data.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Owner(
    @SerializedName("actionUrl")
    val actionUrl: String = "",
    @SerializedName("area")
    val area: Any = Any(),
    @SerializedName("avatar")
    val avatar: String = "",
    @SerializedName("birthday")
    val birthday: Any = Any(),
    @SerializedName("city")
    val city: Any = Any(),
    @SerializedName("country")
    val country: Any = Any(),
    @SerializedName("cover")
    val cover: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("expert")
    val expert: Boolean = false,
    @SerializedName("followed")
    val followed: Boolean = false,
    @SerializedName("gender")
    val gender: String = "",
    @SerializedName("ifPgc")
    val ifPgc: Boolean = false,
    @SerializedName("job")
    val job: Any = Any(),
    @SerializedName("library")
    val library: String = "",
    @SerializedName("limitVideoOpen")
    val limitVideoOpen: Boolean = false,
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("registDate")
    val registDate: Long = 0,
    @SerializedName("releaseDate")
    val releaseDate: Long = 0,
    @SerializedName("uid")
    val uid: Int = 0,
    @SerializedName("university")
    val university: Any = Any(),
    @SerializedName("userType")
    val userType: String = ""
)