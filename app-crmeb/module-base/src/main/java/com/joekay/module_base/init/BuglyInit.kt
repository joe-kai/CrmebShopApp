package com.joekay.module_base.init

import android.content.Context
import androidx.startup.Initializer
import com.tencent.bugly.crashreport.CrashReport

/**
 * @author:  JoeKai
 * @date:  2022/12/5
 * @explain：
 */
class BuglyInit : Initializer<Unit> {
    override fun create(context: Context) {
        // Bugly异常捕捉
        CrashReport.initCrashReport(context, "70d55d6996", false)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}