package com.joekay.network

import android.app.Notification.Builder
import android.text.TextUtils
import android.util.Log
import com.joekay.network.config.*
import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author:  JoeKai
 * @date:  2022/11/21
 * @explain： Retrofit 构建器基类
 */
object RetrofitBuilder {

    private const val LOG_TAG = "Retrofit"

    /**
     * retrofit 多实例
     */
    private val retrofitMap = mutableMapOf<String, Retrofit>()
    private var interceptorList = mutableListOf<Interceptor>()

    private val okHttpClient: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
            .callTimeout(CALL_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .cookieJar(LocalCookieJar())
            .addInterceptor(HttpLoggingInterceptor { message ->
                Logger.t(LOG_TAG)
                if (isJsonObject(message)) {
                    Logger.json(message)
                } else if (message.contains("http://") || message.contains("https://")) {
                    Logger.e(message)
                }
                //Log.d(LOG_TAG,message)
            }.setLevel(HttpLoggingInterceptor.Level.BODY))

        if (interceptorList.isNotEmpty()) {
            interceptorList.forEach {
                builder.addInterceptor(it)
            }
        }
        builder.build()
    }

    /**
     * 2 * 判断字符串是否可以转化为json对象
     * 3 * @param content
     * 4 * @return
     * 5
     */
    private fun isJsonObject(content: String?): Boolean {
        return if (content == null || TextUtils.isEmpty(content)) {
            false
        } else try {
            JSONObject(content)
            true
        } catch (e: Exception) {
            false
        }
    }


    fun getRetrofit(baseUrl: String): Retrofit = retrofitMap.getOrPut(baseUrl) {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun <T> create(serviceClass: Class<T>, baseUrl: String): T =
        getRetrofit(baseUrl).create(serviceClass)


    fun initInterceptor(interceptor: Interceptor) = apply {
        interceptorList.add(interceptor)
    }

}