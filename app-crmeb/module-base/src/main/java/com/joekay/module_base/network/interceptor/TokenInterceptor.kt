package com.joekay.module_base.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author:  JoeKai
 * @date:  2022/12/2
 * @explain：
 */
class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        var response = chain.proceed(request)


        return response
    }
}