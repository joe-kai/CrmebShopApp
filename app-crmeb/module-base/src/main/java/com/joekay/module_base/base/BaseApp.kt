package com.joekay.module_base.base

import com.joekay.lib_base.base.BaseApplication
import com.joekay.lib_network.RetrofitBuilder
import com.joekay.module_base.network.interceptor.HeaderInterceptor

/**
 * @author:  JoeKai
 * @date:  2022/11/21
 * @explain：
 */
open class BaseApp :BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        RetrofitBuilder.initInterceptor(HeaderInterceptor())
    }
}