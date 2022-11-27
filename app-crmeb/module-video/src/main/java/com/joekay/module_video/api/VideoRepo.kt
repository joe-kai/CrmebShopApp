package com.joekay.module_video.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.joekay.module_video.model.Follow
import com.joekay.module_video.ui.VideoPagingSource
import com.joekay.network.repository.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/11/27
 * @explain：
 */
class VideoRepo @Inject constructor(): BaseRepository<VideoApi>(
    VideoApi::class.java,
    baseUrl = "http://baobab.kaiyanapp.com/"
) {
    fun getVideoPagingData(): Flow<PagingData<Follow.Item>> {
        return Pager(
            config = config,
            pagingSourceFactory = { VideoPagingSource(api) }
        ).flow
    }
}