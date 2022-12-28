package com.joekay.module_base.network.api

import com.joekay.module_base.network.interceptor.RefreshTokenInterceptor
import retrofit2.Call
import retrofit2.http.GET

/**
 * @author:  JoeKai
 * @date:  2022/12/28
 * @explain：
 */
interface BaseApi {

    @GET("")
    fun refreshToken(): Call<RefreshTokenInterceptor.RequestCode>
}