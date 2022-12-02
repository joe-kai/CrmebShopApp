package com.joekay.module_base.network.interceptor

import com.joekay.module_base.other.TOKEN_KEY
import com.joekay.module_base.utils.MMKVUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * author:  JoeKai
 * date: 2022/8/3 13:32
 * content：
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .header("content-type", "application/json")
            .header(
                "Authori-zation", MMKVUtils.getString(TOKEN_KEY, "")
            )
            .build()

        return chain.proceed(request)
    }
}