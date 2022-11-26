package com.joekay.app_crmeb

import android.content.Context
import com.joekay.module_base.base.BaseApp
import com.therouter.TheRouter

/**
 * @author:  JoeKai
 * @date:  2022/11/25
 * @explain：
 */
class CrmebApp : BaseApp() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        TheRouter.isDebug = true
    }

}