package com.joekay.module_account.api

import com.joekay.module_account.api.body.LoginBody
import com.joekay.module_account.model.LoginModel
import com.joekay.network.response.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author:  JoeKai
 * @date:  2022/12/26
 * @explain：
 */
interface AccountApi {

    @POST("/api/front/login")
    suspend fun login(@Body body: LoginBody): ApiResponse<LoginModel>
}