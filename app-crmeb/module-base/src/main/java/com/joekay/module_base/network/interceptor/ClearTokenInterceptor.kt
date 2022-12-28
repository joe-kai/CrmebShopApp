package com.joekay.module_base.network.interceptor

import com.joekay.module_base.other.TOKEN_KEY
import com.joekay.module_base.utils.MMKVUtils
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.IOException
import org.json.JSONObject
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException


/**
 * @author:  JoeKai
 * @date:  2022/12/2
 * @explain：
 */
class ClearTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val response = chain.proceed(request)
        if (response.isSuccessful) {
            val jsonObject = JSONObject(getResponseBody(response))
            if (jsonObject.has("code")) {
                val code = jsonObject.getInt("code")
                if (code == 401) {
                    MMKVUtils.put(TOKEN_KEY, "")
                }
            }
        }
        return response
    }

    private fun getResponseBody(response: Response): String {
        val UTF8: Charset = Charset.forName("UTF-8")
        val responseBody: ResponseBody? = response.body
        val source: BufferedSource = responseBody!!.source()
        try {
            source.request(Long.MAX_VALUE) // Buffer the entire body.
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val buffer: Buffer = source.buffer()
        var charset: Charset = UTF8
        val contentType: MediaType? = responseBody.contentType()
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8)!!
            } catch (e: UnsupportedCharsetException) {
                e.printStackTrace()
            }
        }
        return buffer.clone().readString(charset)
    }

}