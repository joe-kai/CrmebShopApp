package com.joekay.module_base.network.interceptor

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
                "Authori-zation","9b1348fc777d414c8453438b8c770b57"
                //DataStoreUtils.getSyncData(
                //    DataStoreKey.TOKEN_KEY,
                //    "9b1348fc777d414c8453438b8c770b57"
                //)
            )
            .build()

        return chain.proceed(request)
    }
}