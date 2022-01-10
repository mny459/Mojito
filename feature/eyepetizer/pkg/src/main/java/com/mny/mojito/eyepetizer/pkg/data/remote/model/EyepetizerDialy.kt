package com.mny.mojito.eyepetizer.pkg.data.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class EyepetizerDialy(
    @SerializedName("adExist")
    val adExist: Boolean = false,
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("itemList")
    val itemList: List<Item> = listOf(),
    @SerializedName("nextPageUrl")
    val nextPageUrl: String = "",
    @SerializedName("total")
    val total: Int = 0
)

@Keep
data class Item(
    @SerializedName("adIndex")
    val adIndex: Int = 0,
    @SerializedName("data")
    val itemData: ItemData = ItemData(),
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("tag")
    val tag: Any = Any(),
    @SerializedName("trackingData")
    val trackingData: Any = Any(),
    @SerializedName("type")
    val type: String = "",
)

@Keep
data class ItemData(
    @SerializedName("actionUrl")
    val actionUrl: String? = "",
    @SerializedName("adTrack")
    val adTrack: Any = Any(),
    @SerializedName("content")
    val content: Content = Content(),
    @SerializedName("dataType")
    val dataType: String = "",
    @SerializedName("follow")
    val follow: Any? = Any(),
    @SerializedName("header")
    val header: Header = Header(),
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("subTitle")
    val subTitle: Any = Any(),
    @SerializedName("text")
    val text: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("titleList")
    val titleList: List<String> = emptyList(),
    @SerializedName("backgroundImage")
    val backgroundImage: String = ""
)