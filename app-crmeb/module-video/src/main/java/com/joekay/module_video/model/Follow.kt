
package com.joekay.module_video.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

/**
 * 社区-关注列表，响应实体类。
 *
 * @author vipyinzhiwei
 * @since  2020/5/2
 */
data class Follow(val itemList: List<Item>, val count: Int, val total: Int, val nextPageUrl: String?, val adExist: Boolean) : Model() {

    data class Item(val `data`: Data, val type: String, val tag: Any?, val id: Int = 0, val adIndex: Int)

    data class Data(val adTrack: List<Any>, val content: Content, val dataType: String, val header: Header, val type: String)

    data class Header(
        val actionUrl: String,
        val followType: String,
        val icon: String?,
        val iconType: String,
        val id: Int,
        val issuerName: String,
        val labelList: Any,
        val showHateVideo: Boolean,
        val tagId: Int,
        val tagName: Any,
        val time: Long,
        val topShow: Boolean
    )
}

data class Content(val adIndex: Int, val data: FollowCard, val id: Int, val tag: Any, val type: String)

data class FollowCard(
    val ad: Boolean,
    val adTrack: List<Any>,
    val author: Author?,
    val brandWebsiteInfo: Any,
    val campaign: Any,
    val category: String,
    val collected: Boolean,
    val consumption: Consumption,
    val cover: Cover,
    val dataType: String,
    val date: Long,
    val description: String,
    val descriptionEditor: String,
    val descriptionPgc: Any,
    val duration: Int,
    val favoriteAdTrack: Any,
    val id: Long,
    val idx: Int,
    val ifLimitVideo: Boolean,
    val label: Any,
    val labelList: List<Any>,
    val lastViewTime: Any,
    val library: String,
    val playInfo: List<PlayInfo>,
    val playUrl: String,
    val played: Boolean,
    val playlists: Any,
    val promotion: Any,
    //val provider: Provider,
    val reallyCollected: Boolean,
    val releaseTime: Long?,
    val remark: String,
    val resourceType: String,
    val searchWeight: Int,
    val shareAdTrack: Any,
    val slogan: Any,
    val src: Any,
    val subtitles: List<Any>,
    //val tags: List<Tag>,
    val thumbPlayUrl: Any,
    val title: String,
    val titlePgc: Any,
    val type: String,
    val waterMarks: Any,
    val webAdTrack: Any,
    val webUrl: WebUrl
)
@Parcelize
data class Cover(val blurred: String, val detail: String, val feed: String, val homepage: String?, val sharing: String?) : Parcelable

@Parcelize
data class WebUrl(val forWeibo: String, val raw: String) : Parcelable
@Parcelize
data class Author(
    val adTrack: @RawValue Any?,
    val approvedNotReadyVideoCount: Int,
    val description: String,
    val expert: Boolean,
    val follow: Follow,
    val icon: String?,
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val link: String,
    val name: String,
    val recSort: Int,
    val shield: Shield,
    val videoNum: Int
) : Parcelable {
    @Parcelize
    data class Follow(val followed: Boolean, val itemId: Int, val itemType: String) : Parcelable
    @Parcelize
    data class Shield(val itemId: Int, val itemType: String, val shielded: Boolean) : Parcelable
}
data class PlayInfo(val height: Int, val name: String, val type: String, val url: String, val urlList: List<Url>, val width: Int)

data class Url(val name: String, val size: Int, val url: String)

data class Label(val actionUrl: String?, val text: String?, val card: String, val detail: Any?)

@Parcelize
data class Consumption(val collectionCount: Int, val realCollectionCount: Int, val replyCount: Int, val shareCount: Int) : Parcelable





