package com.joekay.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonToken
import com.joekay.base.aop.Log
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import okhttp3.OkHttpClient
import timber.log.Timber

/**
 * @author:  JoeKai
 * @date:  2022/11/20
 * @explain：
 */
open class BaseApplication : Application() {
    companion object {
        /**
         * 初始化一些第三方框架
         */
        fun initSdk(application: Application) {
            // Activity 栈管理初始化
            ActivityManager.getInstance().init(application)
        }
    }

    @Log("启动耗时")
    override fun onCreate() {
        super.onCreate()
        initSdk(this)
    }
}