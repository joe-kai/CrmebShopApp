package com.joekay.app_crmeb.init

import android.content.Context
import androidx.startup.Initializer
import com.joekay.app_crmeb.BuildConfig
import com.umeng.commonsdk.UMConfigure

/**
 * @author:  JoeKai
 * @date:  2022/12/5
 * @explain：友盟初始化
 */
class UMengInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        UMConfigure.init(context, UMConfigure.DEVICE_TYPE_PHONE, null)
        UMConfigure.setLogEnabled(BuildConfig.DEBUG)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}