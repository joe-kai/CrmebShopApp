package com.joekay.app_crmeb

import android.content.Context
import android.os.MessageQueue.IdleHandler
import cn.jpush.android.api.JPushInterface
import com.joekay.module_base.base.BaseApplication
import com.therouter.TheRouter
import dagger.hilt.android.HiltAndroidApp

/**
 * @author:  JoeKai
 * @date:  2022/11/25
 * @explain：
 */
@HiltAndroidApp
class CrmebApp : BaseApplication() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        TheRouter.isDebug = true
        //TheRouter.build(RouterPath.act_video).navigation()
    }

    override fun onCreate() {
        super.onCreate()
        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)
    }

}
