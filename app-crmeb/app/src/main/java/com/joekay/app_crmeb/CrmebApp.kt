package com.joekay.app_crmeb

import android.content.Context
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

}