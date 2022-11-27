package com.joekay.module_video.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author:  JoeKai
 * @date:  2022/11/27
 * @explain：
 */
@Parcelize
data class VideoInfo(
    val videoId: Long,
    val playUrl: String,
    val title: String,
    val description: String,
    val category: String,
    val library: String,
    val consumption: Consumption,
    val cover: Cover,
    val author: Author?,
    val webUrl: WebUrl
) : Parcelable
