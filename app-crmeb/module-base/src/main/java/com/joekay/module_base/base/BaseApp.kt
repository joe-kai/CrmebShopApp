package com.joekay.module_base.base

import android.app.Application
import android.content.Context
import com.hjq.bar.TitleBar
import com.joekay.base.ActivityManager
import com.joekay.base.R
import com.joekay.base.gilde.GlideApp
import com.joekay.base.utils.GlobalUtil
import com.joekay.base.widgets.NoStatusFooter
import com.joekay.module_base.network.interceptor.HeaderInterceptor
import com.joekay.module_base.other.TitleBarStyle
import com.joekay.network.RetrofitBuilder
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV

/**
 * @author:  JoeKai
 * @date:  2022/11/21
 * @explain：
 */
open class BaseApp : Application() {
    companion object {
        /**
         * 初始化一些第三方框架
         */
        fun initSdk(application: Application) {
            ////初始化腾讯MMKV(数据存储)
            //val dir: String = getFilesDir().getAbsolutePath() + "/mmkv"
            //MMKV.initialize(
            //    dir
            //) { libName -> ReLinker.loadLibrary(application, libName) }
            MMKV.initialize(application)
            // 设置标题栏初始化器
            TitleBar.setDefaultStyle(TitleBarStyle())
            // 设置全局初始化器
            SmartRefreshLayout.setDefaultRefreshInitializer { context: Context, layout: RefreshLayout ->
                // 刷新头部是否跟随内容偏移
                layout.setEnableLoadMore(true)
                layout.setEnableLoadMoreWhenContentNotFull(true)
                //layout.setEnableHeaderTranslationContent(true)
                //    // 刷新尾部是否跟随内容偏移
                //    .setEnableFooterTranslationContent(true)
                //    // 加载更多是否跟随内容偏移
                //    .setEnableFooterFollowWhenNoMoreData(true)
                //    // 内容不满一页时是否可以上拉加载更多
                //    .setEnableLoadMoreWhenContentNotFull(true)
                //    // 仿苹果越界效果开关
                //    .setEnableOverScrollDrag(false)
                //    .setEnableLoadMore(true)
            }

            // 设置全局的 Header 构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context: Context, layout: RefreshLayout ->
                layout.setEnableHeaderTranslationContent(true)
                layout.setPrimaryColorsId(
                    R.color.refresh_header_color,
                    R.color.refresh_header_text_color,
                ) //全局设置主题颜色
                ClassicsHeader(context)
            }
            // 设置全局的 Footer 构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context: Context, layout: RefreshLayout ->
                layout.setEnableFooterFollowWhenNoMoreData(true)
                layout.setEnableFooterTranslationContent(true)
                layout.setFooterHeight(153f)
                layout.setFooterTriggerRate(0.6f)
                NoStatusFooter.REFRESH_FOOTER_NOTHING =
                    GlobalUtil.getString(R.string.footer_not_more)
                NoStatusFooter(context).apply {
                    setAccentColorId(R.color.colorAccent)
                    setTextTitleSize(16f)
                }
            }

            // Activity 栈管理初始化
            ActivityManager.getInstance().init(application)
            // Bugly异常捕捉
            CrashReport.initCrashReport(application, "70d55d6996", false)
        }
    }


    override fun onCreate() {
        super.onCreate()
        initSdk(this)

        RetrofitBuilder.initInterceptor(HeaderInterceptor())
    }

    override fun onLowMemory() {
        super.onLowMemory()
        // 清理所有图片内存缓存
        GlideApp.get(this).onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        // 根据手机内存剩余情况清理图片内存缓存
        GlideApp.get(this).onTrimMemory(level)
    }
}