package com.joekay.module_base.base

import TitleBarAction
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.TitleBar
import com.joekay.base.ActivityManager
import com.joekay.base.action.ResourcesAction
import com.joekay.base.fragment.BaseBindingFrag
import com.joekay.base.ext.logD
import com.joekay.module_base.event.MessageEvent
import com.joekay.network.event.LoadingEvent
import com.therouter.TheRouter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author:  JoeKai
 * @date:  2022/11/20
 * @explain：
 */
abstract class BaseFragment<VB : ViewBinding> : BaseBindingFrag<VB>(), TitleBarAction,
    ResourcesAction {
    /** 标题栏对象 */
    private var titleBar: TitleBar? = null

    /**
     * 日志输出标志
     */
    protected val TAG: String = "Log:${this.javaClass.simpleName}->"

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TheRouter.inject(this)
        val titleBar = getTitleBar()
        ImmersionBar.setTitleBar(getAttachActivity(), titleBar)
        // 设置标题栏点击监听
        titleBar?.setOnTitleBarListener(this)

    }

    override fun getTitleBar(): TitleBar? {
        if (titleBar == null || !isLoading()) {
            titleBar = obtainTitleBar(view as ViewGroup)
        }
        return titleBar
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(messageEvent: MessageEvent) {
        logD(TAG, "BaseFragment-->onMessageEvent()")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    private fun loadingEvent(event: LoadingEvent) {
        if (event.isLoading) {
            showDialog()
        } else {
            dismissDialog()
        }
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
        logD(TAG, "BaseActivity-->onStop()")
    }
}