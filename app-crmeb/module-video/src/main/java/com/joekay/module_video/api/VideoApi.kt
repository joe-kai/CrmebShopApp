package com.joekay.module_video.api

import com.joekay.module_video.model.Follow
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * @author:  JoeKai
 * @date:  2022/11/27
 * @explain：
 */
interface VideoApi {
    /**
     * 社区-关注列表
     */

    @GET
    suspend fun getFollow(@Url url: String): Follow
}