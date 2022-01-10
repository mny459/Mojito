package com.mny.mojito.eyepetizer.pkg.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.entension.inflate
import com.mny.mojito.eyepetizer.pkg.R
import com.mny.mojito.eyepetizer.pkg.data.remote.model.Item
import com.mny.mojito.eyepetizer.pkg.databinding.ItemTextCardTypeHeaderFiveBinding


/**
 * 项目通用ViewHolder，集中统一管理。
 *
 * @author vipyinzhiwei
 * @since  2020/5/1
 */

/**
 * 未知类型，占位进行容错处理。
 */
class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view)

class TextCardViewHeader5ViewHolder(val binding: ItemTextCardTypeHeaderFiveBinding) :
    RecyclerView.ViewHolder(binding.root)

class TextCardViewHeader7ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvTitle7 = view.findViewById<TextView>(R.id.tvTitle7)
    val tvRightText7 = view.findViewById<TextView>(R.id.tvRightText7)
    val ivInto7 = view.findViewById<ImageView>(R.id.ivInto7)
}

class TextCardViewHeader8ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvTitle8 = view.findViewById<TextView>(R.id.tvTitle8)
    val tvRightText8 = view.findViewById<TextView>(R.id.tvRightText8)
    val ivInto8 = view.findViewById<ImageView>(R.id.ivInto8)
}

class TextCardViewFooter2ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvFooterRightText2 = view.findViewById<TextView>(R.id.tvFooterRightText2)
    val ivTooterInto2 = view.findViewById<ImageView>(R.id.ivTooterInto2)
}

class TextCardViewFooter3ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvRefresh = view.findViewById<TextView>(R.id.tvRefresh)
    val tvFooterRightText3 = view.findViewById<TextView>(R.id.tvFooterRightText3)
    val ivTooterInto3 = view.findViewById<ImageView>(R.id.ivTooterInto3)
}

//class HorizontalScrollCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//    val bannerViewPager: BannerViewPager<Discovery.ItemX, DiscoveryAdapter.HorizontalScrollCardAdapter.ViewHolder> = view.findViewById(R.id.bannerViewPager)
//}

class SpecialSquareCardCollectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val tvRightText = view.findViewById<TextView>(R.id.tvRightText)
    val ivInto = view.findViewById<ImageView>(R.id.ivInto)
}

class ColumnCardListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val tvRightText = view.findViewById<TextView>(R.id.tvRightText)
    val ivInto = view.findViewById<ImageView>(R.id.ivInto)
}

class FollowCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivVideo = view.findViewById<ImageView>(R.id.ivVideo)
    val ivAvatar = view.findViewById<ImageView>(R.id.ivAvatar)
    val ivAvatarStar = view.findViewById<ImageView>(R.id.ivAvatarStar)
    val tvVideoDuration = view.findViewById<TextView>(R.id.tvVideoDuration)
    val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val ivShare = view.findViewById<ImageView>(R.id.ivShare)
    val tvLabel = view.findViewById<TextView>(R.id.tvLabel)
    val ivChoiceness = view.findViewById<ImageView>(R.id.ivChoiceness)
}

class Banner3ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivPicture = view.findViewById<ImageView>(R.id.ivPicture)
    val tvLabel = view.findViewById<TextView>(R.id.tvLabel)
    val ivAvatar = view.findViewById<ImageView>(R.id.ivAvatar)
    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
}

class VideoSmallCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivPicture = view.findViewById<ImageView>(R.id.ivPicture)
    val tvVideoDuration = view.findViewById<TextView>(R.id.tvVideoDuration)
    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
    val ivShare = view.findViewById<ImageView>(R.id.ivShare)
}

class TagBriefCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivPicture = view.findViewById<ImageView>(R.id.ivPicture)
    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
    val tvFollow = view.findViewById<TextView>(R.id.tvFollow)
}

class TopicBriefCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivPicture = view.findViewById<ImageView>(R.id.ivPicture)
    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
}

class InformationCardFollowCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivCover = view.findViewById<ImageView>(R.id.ivCover)
    val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
}

class AutoPlayVideoAdViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvLabel = view.findViewById<TextView>(R.id.tvLabel)
    val ivAvatar = view.findViewById<ImageView>(R.id.ivAvatar)
    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
//    val videoPlayer: GSYVideoPlayer = view.findViewById(R.id.videoPlayer)
}

class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivPicture = view.findViewById<ImageView>(R.id.ivPicture)
}

class UgcSelectedCardCollectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val tvRightText = view.findViewById<TextView>(R.id.tvRightText)
    val ivCoverLeft = view.findViewById<ImageView>(R.id.ivCoverLeft)
    val ivLayersLeft = view.findViewById<ImageView>(R.id.ivLayersLeft)
    val ivAvatarLeft = view.findViewById<ImageView>(R.id.ivAvatarLeft)
    val tvNicknameLeft = view.findViewById<TextView>(R.id.tvNicknameLeft)
    val ivCoverRightTop = view.findViewById<ImageView>(R.id.ivCoverRightTop)
    val ivLayersRightTop = view.findViewById<ImageView>(R.id.ivLayersRightTop)
    val ivAvatarRightTop = view.findViewById<ImageView>(R.id.ivAvatarRightTop)
    val tvNicknameRightTop = view.findViewById<TextView>(R.id.tvNicknameRightTop)
    val ivCoverRightBottom = view.findViewById<ImageView>(R.id.ivCoverRightBottom)
    val ivLayersRightBottom = view.findViewById<ImageView>(R.id.ivLayersRightBottom)
    val ivAvatarRightBottom = view.findViewById<ImageView>(R.id.ivAvatarRightBottom)
    val tvNicknameRightBottom = view.findViewById<TextView>(R.id.tvNicknameRightBottom)
}

/**
 * RecyclerView帮助类，获取通用ViewHolder或ItemViewType。
 */
object RecyclerViewHelp {

    fun getViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        ItemViewType.TEXT_CARD_HEADER5 -> TextCardViewHeader5ViewHolder(
            ItemTextCardTypeHeaderFiveBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        ItemViewType.TEXT_CARD_HEADER7 -> TextCardViewHeader7ViewHolder(
            R.layout.item_text_card_type_header_seven.inflate(
                parent
            )
        )
        ItemViewType.TEXT_CARD_HEADER8 -> TextCardViewHeader8ViewHolder(
            R.layout.item_text_card_type_header_eight.inflate(
                parent
            )
        )
        ItemViewType.TEXT_CARD_FOOTER2 -> TextCardViewFooter2ViewHolder(
            R.layout.item_text_card_type_footer_two.inflate(
                parent
            )
        )
        ItemViewType.TEXT_CARD_FOOTER3 -> TextCardViewFooter3ViewHolder(
            R.layout.item_text_card_type_footer_three.inflate(
                parent
            )
        )
//        ItemViewType.HORIZONTAL_SCROLL_CARD -> HorizontalScrollCardViewHolder(
//            R.layout.item_horizontal_scroll_card_type.inflate(
//                parent
//            )
//        )
        ItemViewType.SPECIAL_SQUARE_CARD_COLLECTION -> SpecialSquareCardCollectionViewHolder(
            R.layout.item_special_square_card_collection_type.inflate(
                parent
            )
        )
        ItemViewType.COLUMN_CARD_LIST -> ColumnCardListViewHolder(
            R.layout.item_column_card_list_type.inflate(
                parent
            )
        )
        ItemViewType.BANNER -> BannerViewHolder(R.layout.item_banner_type.inflate(parent))
        ItemViewType.BANNER3 -> Banner3ViewHolder(R.layout.item_banner_three_type.inflate(parent))
        ItemViewType.VIDEO_SMALL_CARD -> VideoSmallCardViewHolder(
            R.layout.item_video_small_card_type.inflate(
                parent
            )
        )
        ItemViewType.TAG_BRIEFCARD -> TagBriefCardViewHolder(
            R.layout.item_brief_card_tag_brief_card_type.inflate(
                parent
            )
        )
        ItemViewType.TOPIC_BRIEFCARD -> TopicBriefCardViewHolder(
            R.layout.item_brief_card_topic_brief_card_type.inflate(
                parent
            )
        )
        ItemViewType.FOLLOW_CARD -> FollowCardViewHolder(
            R.layout.item_follow_card_type.inflate(
                parent
            )
        )
        ItemViewType.INFORMATION_CARD -> InformationCardFollowCardViewHolder(
            R.layout.item_information_card_type.inflate(
                parent
            )
        )
        ItemViewType.UGC_SELECTED_CARD_COLLECTION -> UgcSelectedCardCollectionViewHolder(
            R.layout.item_ugc_selected_card_collection_type.inflate(
                parent
            )
        )
        ItemViewType.AUTO_PLAY_VIDEO_AD -> AutoPlayVideoAdViewHolder(
            R.layout.item_auto_play_video_ad.inflate(
                parent
            )
        )

        else -> EmptyViewHolder(View(parent.context))
    }

    private fun getItemViewType(type: String, dataType: String): Int {
        return when (type) {
            "horizontalScrollCard" -> {
                when (dataType) {
                    "HorizontalScrollCard" -> ItemViewType.HORIZONTAL_SCROLL_CARD
                    else -> ItemViewType.UNKNOWN
                }
            }
            "specialSquareCardCollection" -> {
                when (dataType) {
                    "ItemCollection" -> ItemViewType.SPECIAL_SQUARE_CARD_COLLECTION
                    else -> ItemViewType.UNKNOWN
                }
            }
            "columnCardList" -> {
                when (dataType) {
                    "ItemCollection" -> ItemViewType.COLUMN_CARD_LIST
                    else -> ItemViewType.UNKNOWN
                }
            }
            /*"textCard" -> {
                when (item.data.type) {
                    "header5" -> TEXT_CARD_HEADER5
                    "header7" -> TEXT_CARD_HEADER7
                    "header8" -> TEXT_CARD_HEADER8
                    "footer2" -> TEXT_CARD_FOOTER2
                    "footer3" -> TEXT_CARD_FOOTER3
                    else -> UNKNOWN
                }
            }*/
            "banner" -> {
                when (dataType) {
                    "Banner" -> ItemViewType.BANNER
                    else -> ItemViewType.UNKNOWN
                }
            }
            "banner3" -> {
                when (dataType) {
                    "Banner" -> ItemViewType.BANNER3
                    else -> ItemViewType.UNKNOWN
                }
            }
            "videoSmallCard" -> {
                when (dataType) {
                    "VideoBeanForClient" -> ItemViewType.VIDEO_SMALL_CARD
                    else -> ItemViewType.UNKNOWN
                }
            }
            "briefCard" -> {
                when (dataType) {
                    "TagBriefCard" -> ItemViewType.TAG_BRIEFCARD
                    "TopicBriefCard" -> ItemViewType.TOPIC_BRIEFCARD
                    else -> ItemViewType.UNKNOWN
                }
            }
            "followCard" -> {
                when (dataType) {
                    "FollowCard" -> ItemViewType.FOLLOW_CARD
                    else -> ItemViewType.UNKNOWN
                }
            }
            "informationCard" -> {
                when (dataType) {
                    "InformationCard" -> ItemViewType.INFORMATION_CARD
                    else -> ItemViewType.UNKNOWN
                }
            }
            "ugcSelectedCardCollection" -> {
                when (dataType) {
                    "ItemCollection" -> ItemViewType.UGC_SELECTED_CARD_COLLECTION
                    else -> ItemViewType.UNKNOWN
                }
            }
            "autoPlayVideoAd" -> {
                when (dataType) {
                    "AutoPlayVideoAdDetail" -> ItemViewType.AUTO_PLAY_VIDEO_AD
                    else -> ItemViewType.UNKNOWN
                }
            }
            else -> ItemViewType.UNKNOWN
        }
    }

    private fun getTextCardType(type: String) = when (type) {
        "header4" -> ItemViewType.TEXT_CARD_HEADER4
        "header5" -> ItemViewType.TEXT_CARD_HEADER5
        "header7" -> ItemViewType.TEXT_CARD_HEADER7
        "header8" -> ItemViewType.TEXT_CARD_HEADER8
        "footer2" -> ItemViewType.TEXT_CARD_FOOTER2
        "footer3" -> ItemViewType.TEXT_CARD_FOOTER3
        else -> ItemViewType.UNKNOWN
    }


    fun getItemViewType(item: Item): Int {
        val itemType = item.type
        LogUtils.d("getItemViewType itemType = $itemType itemDataType = ${item.itemData.type} itemDataDataType = ${item.itemData.dataType}")
        return if (itemType == "textCard") getTextCardType(item.itemData.type)
        else getItemViewType(itemType, item.itemData.dataType)
    }

}

object ItemViewType {
    const val UNKNOWN = -1              //未知类型，使用EmptyViewHolder容错处理。

    const val CUSTOM_HEADER = 0         //自定义头部类型。

    const val TEXT_CARD_HEADER1 = 1

    const val TEXT_CARD_HEADER2 = 2

    const val TEXT_CARD_HEADER3 = 3

    const val TEXT_CARD_HEADER4 = 4     //type:textCard -> dataType:TextCard,type:header4

    const val TEXT_CARD_HEADER5 = 5     //type:textCard -> dataType:TextCard -> type:header5

    const val TEXT_CARD_HEADER6 = 6

    const val TEXT_CARD_HEADER7 =
        7    //type:textCard -> dataType:TextCardWithRightAndLeftTitle,type:header7

    const val TEXT_CARD_HEADER8 =
        8    //type:textCard -> dataType:TextCardWithRightAndLeftTitle,type:header8

    const val TEXT_CARD_FOOTER1 = 9

    const val TEXT_CARD_FOOTER2 = 10    //type:textCard -> dataType:TextCard,type:footer2

    const val TEXT_CARD_FOOTER3 = 11    //type:textCard -> dataType:TextCardWithTagId,type:footer3

    const val BANNER = 12               //type:banner -> dataType:Banner

    const val BANNER3 = 13              //type:banner3-> dataType:Banner

    const val FOLLOW_CARD =
        14          //type:followCard -> dataType:FollowCard -> type:video -> dataType:VideoBeanForClient

    const val TAG_BRIEFCARD = 15        //type:briefCard -> dataType:TagBriefCard

    const val TOPIC_BRIEFCARD = 16      //type:briefCard -> dataType:TopicBriefCard

    const val COLUMN_CARD_LIST = 17      //type:columnCardList -> dataType:ItemCollection

    const val VIDEO_SMALL_CARD = 18     //type:videoSmallCard -> dataType:VideoBeanForClient

    const val INFORMATION_CARD = 19     //type:informationCard -> dataType:InformationCard

    const val AUTO_PLAY_VIDEO_AD = 20   //type:autoPlayVideoAd -> dataType:AutoPlayVideoAdDetail

    const val HORIZONTAL_SCROLL_CARD =
        21    //type:horizontalScrollCard -> dataType:HorizontalScrollCard

    const val SPECIAL_SQUARE_CARD_COLLECTION =
        22   //type:specialSquareCardCollection -> dataType:ItemCollection

    const val UGC_SELECTED_CARD_COLLECTION =
        23   //type:ugcSelectedCardCollection -> dataType:ItemCollection

    const val MAX = 100   //避免外部其他类型与此处包含的某个类型重复。

}

object ActionUrl {

    const val TAG = "eyepetizer://tag/"

    const val DETAIL = "eyepetizer://detail/"

    const val RANKLIST = "eyepetizer://ranklist/"

    const val WEBVIEW = "eyepetizer://webview/?title="

    const val REPLIES_HOT = "eyepetizer://replies/hot?"

    const val TOPIC_DETAIL = "eyepetizer://topic/detail?"

    const val COMMON_TITLE = "eyepetizer://common/?title"

    const val LT_DETAIL = "eyepetizer://lightTopic/detail/"

    const val CM_TOPIC_SQUARE = "eyepetizer://community/topicSquare"

    const val HP_NOTIFI_TAB_ZERO = "eyepetizer://homepage/notification?tabIndex=0"

    const val CM_TAGSQUARE_TAB_ZERO = "eyepetizer://community/tagSquare?tabIndex=0"

    const val CM_TOPIC_SQUARE_TAB_ZERO = "eyepetizer://community/tagSquare?tabIndex=0"

    const val HP_SEL_TAB_TWO_NEWTAB_MINUS_THREE =
        "eyepetizer://homepage/selected?tabIndex=2&newTabIndex=-3"
}

