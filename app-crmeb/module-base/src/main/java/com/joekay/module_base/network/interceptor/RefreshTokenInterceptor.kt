package com.joekay.module_base.network.interceptor

import com.google.gson.Gson
import com.joekay.base.ext.showToast
import com.joekay.module_base.network.api.BaseApi
import com.joekay.module_base.other.TOKEN_KEY
import com.joekay.module_base.utils.MMKVUtils
import com.joekay.network.RetrofitBuilder
import com.joekay.network.config.BASE_URL
import com.youth.banner.util.LogUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import retrofit2.Call


/**
 * @author:  JoeKai
 * @date:  2022/12/28
 * @explain：
 */
class RefreshTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request.Builder = chain.request().newBuilder()
        //添加默认的Token请求头
        request.addHeader("content-type", "application/json")
        request.addHeader("Authori-zation", MMKVUtils.getString(TOKEN_KEY, ""))
        val proceed = chain.proceed(request.build())
        val mediaType = proceed.body!!.contentType()
        //如果token过期 再去重新请求token 然后设置token的请求头 重新发起请求 用户无感
        val content = proceed.body!!.string()
        if (isTokenExpired(content)) {
            val newToken: String? = getNewToken()
            MMKVUtils.put(TOKEN_KEY, newToken)
            //使用新的Token，创建新的请求
            //使用新的Token，创建新的请求
            val newRequest = chain.request().newBuilder()
                .addHeader("Authori-zation", MMKVUtils.getString(TOKEN_KEY, ""))
                .build()
            return chain.proceed(newRequest);
        }

        return proceed.newBuilder()
            .body(okhttp3.ResponseBody.create(mediaType, content))
            .build()
    }

    private fun getNewToken(): String? {
        // 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求
        val service = RetrofitBuilder.getRetrofit(BASE_URL).create(BaseApi::class.java)
        val call: Call<RequestCode> = service.refreshToken()
        //要用retrofit的同步方式
        var newToken: RequestCode? = null
        try {
            newToken = call.execute().body()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return newToken?.token
    }

    /**
     * 根据Response，判断Token是否失效
     *
     * @return
     */
    private fun isTokenExpired(resultStr: String): Boolean {
        val requestCode: RequestCode = Gson().fromJson(resultStr, RequestCode::class.java)
        if (requestCode.code == 401) {
            LogUtils.e("Token登录过期了")
            MMKVUtils.put(TOKEN_KEY, "")
            "Token登录过期了".showToast()
            return true
        }
        return false
    }

    data class RequestCode(
        val code: Int,
        val token: String,
    )
}