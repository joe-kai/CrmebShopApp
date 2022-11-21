package com.joekay.lib_base.base

import android.app.Application
import android.content.Context

/**
 * @author:  JoeKai
 * @date:  2022/11/20
 * @explain：
 */
open class BaseApplication:Application() {
    companion object {
        lateinit var instance: BaseApplication
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        instance = this
    }
}