package com.joekay.module_main.api

import com.joekay.module_main.model.UpdateApkModel
import com.joekay.network.response.ApiResponse
import retrofit2.http.GET

/**
 * @author:  JoeKai
 * @date:  2022/11/26
 * @explain：
 */
interface UpdateApkApi {
    @GET("/apk/update.json")
    suspend fun getUpdateApk(
    ): ApiResponse<UpdateApkModel>
}