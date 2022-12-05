package com.joekay.network

import android.util.Log
import com.joekay.network.config.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author:  JoeKai
 * @date:  2022/11/21
 * @explain： Retrofit 构建器基类
 */
object RetrofitBuilder {
    private const val LOG_TAG = "---->:"

    /**
     * retrofit 多实例
     */
    private val retrofitMap = mutableMapOf<String, Retrofit>()
    private var interceptorList = mutableListOf<Interceptor>()

    val okHttpClient: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
            .callTimeout(CALL_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .cookieJar(LocalCookieJar())
            .addInterceptor(HttpLoggingInterceptor { message ->
                Log.d(
                    LOG_TAG, message
                )
            }.setLevel(HttpLoggingInterceptor.Level.BODY))

        if (interceptorList.isNotEmpty()) {
            interceptorList.forEach {
                builder.addInterceptor(it)
            }
        }
        builder.build()
    }

    fun getRetrofit(baseUrl: String): Retrofit = retrofitMap.getOrPut(baseUrl) {
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build()
    }

    fun initInterceptor(interceptor: Interceptor) = apply {
        interceptorList.add(interceptor)
    }

}