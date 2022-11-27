package com.joekay.module_video.ui.details

import androidx.lifecycle.MutableLiveData
import com.joekay.base.vm.BaseViewModel
import com.joekay.module_video.model.VideoInfo
import com.joekay.network.config.Kai_Yan_BASE_URL

/**
 * @author:  JoeKai
 * @date:  2022/11/27
 * @explain：
 */
class VideoDetailViewModel : BaseViewModel() {
    var videoInfoData: VideoInfo? = null

    var videoId: Long = 0L

    private var repliesLiveData_ = MutableLiveData<String>()
    private var videoDetailLiveData_ = MutableLiveData<RequestParam>()
    private var repliesAndRepliesLiveData_ = MutableLiveData<RequestParam>()

    var nextPageUrl: String? = null
    private val VIDEO_REPLIES_URL = "${Kai_Yan_BASE_URL}api/v2/replies/video?videoId="
    fun onRefresh() {
        if (videoInfoData == null) {
            videoDetailLiveData_.value = RequestParam(videoId, "${VIDEO_REPLIES_URL}$videoId")
        } else {
            repliesAndRepliesLiveData_.value = RequestParam(
                videoInfoData?.videoId ?: 0L,
                "${VIDEO_REPLIES_URL}${videoInfoData?.videoId ?: 0L}"
            )
        }
    }

    fun onLoadMore() {
        repliesLiveData_.value = nextPageUrl ?: ""
    }

    inner class RequestParam(val videoId: Long, val repliesUrl: String)
}