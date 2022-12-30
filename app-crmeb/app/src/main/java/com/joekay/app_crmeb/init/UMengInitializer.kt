package com.joekay.app_crmeb.init

import android.content.Context
import androidx.startup.Initializer
import cn.jpush.android.api.JPushInterface
import com.hjq.bar.TitleBar
import com.joekay.app_crmeb.BuildConfig
import com.joekay.base.ActivityManager
import com.joekay.base.R
import com.joekay.base.utils.GlobalUtil
import com.joekay.base.widgets.NoStatusFooter
import com.joekay.module_base.network.interceptor.ClearTokenInterceptor
import com.joekay.module_base.network.interceptor.HeaderInterceptor
import com.joekay.module_base.other.TitleBarStyle
import com.joekay.network.RetrofitBuilder
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.tencent.mmkv.MMKV
import com.umeng.commonsdk.UMConfigure

/**
 * @author:  JoeKai
 * @date:  2022/12/5
 * @explain：友盟初始化
 */
class UMengInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        JPushInterface.setDebugMode(true)
        JPushInterface.init(context)
        UMConfigure.init(context, UMConfigure.DEVICE_TYPE_PHONE, null)
        UMConfigure.setLogEnabled(BuildConfig.DEBUG)
        ////初始化腾讯MMKV(数据存储)
        //val dir: String = getFilesDir().getAbsolutePath() + "/mmkv"
        //MMKV.initialize(
        //    dir
        //) { libName -> ReLinker.loadLibrary(application, libName) }
        MMKV.initialize(context)
        // 设置标题栏初始化器
        TitleBar.setDefaultStyle(TitleBarStyle())
        // 设置全局初始化器
        SmartRefreshLayout.setDefaultRefreshInitializer { _, layout: RefreshLayout ->
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

        RetrofitBuilder.initInterceptor(HeaderInterceptor())
            .initInterceptor(ClearTokenInterceptor())
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}