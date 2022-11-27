package com.joekay.module_video.ui

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.joekay.module_video.api.VideoApi
import com.joekay.module_video.model.Follow

/**
 * @author:  JoeKai
 * @date:  2022/11/27
 * @explain：
 */
class VideoPagingSource(private val api: VideoApi) : PagingSource<String, Follow.Item>() {
    override fun getRefreshKey(state: PagingState<String, Follow.Item>): String? = null

    val FOLLOW_URL = "api/v6/community/tab/follow"

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Follow.Item> {
        return try {
            val page = params.key ?: FOLLOW_URL
            val repoResponse = api.getFollow(page)
            val repoItems = repoResponse.itemList
            val prevKey = null
            val nextKey =
                if (repoItems.isNotEmpty() && !repoResponse.nextPageUrl.isNullOrEmpty()) repoResponse.nextPageUrl else null
            LoadResult.Page(repoItems, prevKey, nextKey)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}