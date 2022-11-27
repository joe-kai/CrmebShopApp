package com.joekay.base

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import com.joekay.base.aop.Log
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.SmartRefreshLayout.setDefaultRefreshInitializer
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.DefaultRefreshInitializer
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


            // 设置全局的 Header 构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context: Context, layout: RefreshLayout ->
                layout.setEnableHeaderTranslationContent(true)
                layout.setPrimaryColorsId(
                    R.color.common_accent_color,
                    android.R.color.white
                ) //全局设置主题颜色
                ClassicsHeader(context)
            }
            // 设置全局的 Footer 构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context: Context, layout: RefreshLayout ->
                layout.setEnableFooterFollowWhenNoMoreData(true)
                layout.setEnableFooterTranslationContent(true)
                layout.setFooterHeight(60f)
                layout.setFooterTriggerRate(0.6f)
                ClassicsFooter(context).setDrawableSize(20f)
            }
            // 设置全局初始化器
            SmartRefreshLayout.setDefaultRefreshInitializer { context: Context, layout: RefreshLayout ->
                // 刷新头部是否跟随内容偏移
                layout.setEnableHeaderTranslationContent(true)
                    // 刷新尾部是否跟随内容偏移
                    .setEnableFooterTranslationContent(true)
                    // 加载更多是否跟随内容偏移
                    .setEnableFooterFollowWhenNoMoreData(true)
                    // 内容不满一页时是否可以上拉加载更多
                    .setEnableLoadMoreWhenContentNotFull(false)
                    // 仿苹果越界效果开关
                    .setEnableOverScrollDrag(false)
                    .setEnableLoadMore(true)
            }
            // Activity 栈管理初始化
            ActivityManager.getInstance().init(application)
            // Bugly异常捕捉
            CrashReport.initCrashReport(application, "70d55d6996", false)
        }
    }


    @Log("启动耗时")
    override fun onCreate() {
        super.onCreate()
        initSdk(this)
    }
}