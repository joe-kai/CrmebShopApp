package com.joekay.base

import android.app.Application
import com.joekay.base.aop.Log
import com.tencent.bugly.crashreport.CrashReport

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
            // Bugly异常捕捉
            CrashReport.initCrashReport(application,"70d55d6996",false)
        }
    }

    @Log("启动耗时")
    override fun onCreate() {
        super.onCreate()
        initSdk(this)
    }
}