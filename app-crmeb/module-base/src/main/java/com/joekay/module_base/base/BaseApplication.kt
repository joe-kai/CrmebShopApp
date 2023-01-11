package com.joekay.module_base.base

import android.app.Activity
import android.app.Application
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.View
import android.graphics.Paint
import com.joekay.base.ActivityManager
import com.joekay.base.gilde.GlideApp
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

/**
 * @author:  JoeKai
 * @date:  2022/11/21
 * @explain：
 */
open class BaseApplication : Application() {
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
            //MMKV.initialize(application)
            // 设置标题栏初始化器
            //TitleBar.setDefaultStyle(TitleBarStyle())
            // 设置全局初始化器
            //SmartRefreshLayout.setDefaultRefreshInitializer { _, layout: RefreshLayout ->
            //    // 刷新头部是否跟随内容偏移
            //    layout.setEnableLoadMore(true)
            //    layout.setEnableLoadMoreWhenContentNotFull(true)
            //    //layout.setEnableHeaderTranslationContent(true)
            //    //    // 刷新尾部是否跟随内容偏移
            //    //    .setEnableFooterTranslationContent(true)
            //    //    // 加载更多是否跟随内容偏移
            //    //    .setEnableFooterFollowWhenNoMoreData(true)
            //    //    // 内容不满一页时是否可以上拉加载更多
            //    //    .setEnableLoadMoreWhenContentNotFull(true)
            //    //    // 仿苹果越界效果开关
            //    //    .setEnableOverScrollDrag(false)
            //    //    .setEnableLoadMore(true)
            //}
            //
            //// 设置全局的 Header 构建器
            //SmartRefreshLayout.setDefaultRefreshHeaderCreator { context: Context, layout: RefreshLayout ->
            //    layout.setEnableHeaderTranslationContent(true)
            //    layout.setPrimaryColorsId(
            //        R.color.refresh_header_color,
            //        R.color.refresh_header_text_color,
            //    ) //全局设置主题颜色
            //    ClassicsHeader(context)
            //}
            //// 设置全局的 Footer 构建器
            //SmartRefreshLayout.setDefaultRefreshFooterCreator { context: Context, layout: RefreshLayout ->
            //    layout.setEnableFooterFollowWhenNoMoreData(true)
            //    layout.setEnableFooterTranslationContent(true)
            //    layout.setFooterHeight(153f)
            //    layout.setFooterTriggerRate(0.6f)
            //    NoStatusFooter.REFRESH_FOOTER_NOTHING =
            //        GlobalUtil.getString(R.string.footer_not_more)
            //    NoStatusFooter(context).apply {
            //        setAccentColorId(R.color.colorAccent)
            //        setTextTitleSize(16f)
            //    }
            //}

            // Activity 栈管理初始化
            ActivityManager.getInstance().init(application)
        }
    }


    override fun onCreate() {
        super.onCreate()
        //initSdk(this)
        // Activity 栈管理初始化
        ActivityManager.getInstance().init(this)

        val f = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .tag("-ShopApp")
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(f))
        //setBlackOrWhiteScreen()
    }

    /**
     * 通过监听Activity生命周期实现黑白屏
     * 改变根布局饱和度
     */
    private fun setBlackOrWhiteScreen() {
        var mPaint = Paint()
        var mColorMatrix = ColorMatrix()
        mColorMatrix.setSaturation(0f)
        mPaint.colorFilter = ColorMatrixColorFilter(mColorMatrix)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                val view = activity.window.decorView
                view.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint)
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

        })
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

