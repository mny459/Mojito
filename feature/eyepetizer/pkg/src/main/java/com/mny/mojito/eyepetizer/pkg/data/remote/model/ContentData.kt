package com.mny.mojito.eyepetizer.pkg.data.remote.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ContentData(
    @SerializedName("ad")
    val ad: Boolean = false,
    @SerializedName("adTrack")
    val adTrack: List<Any> = listOf(),
    @SerializedName("addWatermark")
    val addWatermark: Boolean = false,
    @SerializedName("area")
    val area: String = "",
    @SerializedName("author")
    val author: Author? = Author(),
    @SerializedName("brandWebsiteInfo")
    val brandWebsiteInfo: Any = Any(),
    @SerializedName("campaign")
    val campaign: Any = Any(),
    @SerializedName("category")
    val category: String = "",
    @SerializedName("checkStatus")
    val checkStatus: String = "",
    @SerializedName("city")
    val city: String = "",
    @SerializedName("collected")
    val collected: Boolean = false,
    @SerializedName("consumption")
    val consumption: Consumption = Consumption(),
    @SerializedName("cover")
    val cover: Cover = Cover(),
    @SerializedName("createTime")
    val createTime: Long = 0,
    @SerializedName("dataType")
    val dataType: String = "",
    @SerializedName("date")
    val date: Long = 0,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("descriptionEditor")
    val descriptionEditor: String = "",
    @SerializedName("descriptionPgc")
    val descriptionPgc: Any = Any(),
    @SerializedName("duration")
    val duration: Int = 0,
    @SerializedName("favoriteAdTrack")
    val favoriteAdTrack: Any = Any(),
    @SerializedName("height")
    val height: Int = 0,
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("idx")
    val idx: Int = 0,
    @SerializedName("ifLimitVideo")
    val ifLimitVideo: Boolean = false,
    @SerializedName("ifMock")
    val ifMock: Boolean = false,
    @SerializedName("label")
    val label: Any = Any(),
    @SerializedName("labelList")
    val labelList: List<Any> = listOf(),
    @SerializedName("lastViewTime")
    val lastViewTime: Any = Any(),
    @SerializedName("latitude")
    val latitude: Double = 0.0,
    @SerializedName("library")
    val library: String = "",
    @SerializedName("longitude")
    val longitude: Double = 0.0,
    @SerializedName("owner")
    val owner: Owner = Owner(),
    @SerializedName("playInfo")
    val playInfo: List<PlayInfo> = listOf(),
    @SerializedName("playUrl")
    val playUrl: String = "",
    @SerializedName("playUrlWatermark")
    val playUrlWatermark: String = "",
    @SerializedName("played")
    val played: Boolean = false,
    @SerializedName("playlists")
    val playlists: Any = Any(),
    @SerializedName("privateMessageActionUrl")
    val privateMessageActionUrl: Any = Any(),
    @SerializedName("promotion")
    val promotion: Any = Any(),
    @SerializedName("provider")
    val provider: Provider = Provider(),
    @SerializedName("reallyCollected")
    val reallyCollected: Boolean = false,
    @SerializedName("recallSource")
    val recallSource: Any = Any(),
    @SerializedName("recall_source")
    val recallSource1: Any = Any(),
    @SerializedName("recentOnceReply")
    val recentOnceReply: Any = Any(),
    @SerializedName("releaseTime")
    val releaseTime: Long = 0,
    @SerializedName("remark")
    val remark: Any = Any(),
    @SerializedName("resourceType")
    val resourceType: String = "",
    @SerializedName("searchWeight")
    val searchWeight: Int = 0,
    @SerializedName("selectedTime")
    val selectedTime: Long = 0,
    @SerializedName("shareAdTrack")
    val shareAdTrack: Any = Any(),
    @SerializedName("slogan")
    val slogan: Any = Any(),
    @SerializedName("src")
    val src: Any = Any(),
    @SerializedName("status")
    val status: Any = Any(),
    @SerializedName("subtitles")
    val subtitles: List<Any> = listOf(),
    @SerializedName("tags")
    val tags: List<Tag> = listOf(),
    @SerializedName("thumbPlayUrl")
    val thumbPlayUrl: Any = Any(),
    @SerializedName("title")
    val title: String = "",
    @SerializedName("titlePgc")
    val titlePgc: Any = Any(),
    @SerializedName("transId")
    val transId: Any = Any(),
    @SerializedName("type")
    val type: String = "",
    @SerializedName("uid")
    val uid: Int = 0,
    @SerializedName("updateTime")
    val updateTime: Long = 0,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("urls")
    val urls: List<String> = listOf(),
    @SerializedName("urlsWithWatermark")
    val urlsWithWatermark: List<String> = listOf(),
    @SerializedName("validateResult")
    val validateResult: String = "",
    @SerializedName("validateStatus")
    val validateStatus: String = "",
    @SerializedName("validateTaskId")
    val validateTaskId: String = "",
    @SerializedName("videoPosterBean")
    val videoPosterBean: Any = Any(),
    @SerializedName("waterMarks")
    val waterMarks: Any = Any(),
    @SerializedName("webAdTrack")
    val webAdTrack: Any = Any(),
    @SerializedName("webUrl")
    val webUrl: WebUrl = WebUrl(),
    @SerializedName("width")
    val width: Int = 0
)