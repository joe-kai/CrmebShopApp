package com.joekay.module_base.base

import TitleBarAction
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.joekay.base.fragment.BaseBindingFrag
import com.joekay.base.ext.logD
import com.joekay.base.widgets.ActionBarView
import com.joekay.module_base.event.MessageEvent
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author:  JoeKai
 * @date:  2022/11/20
 * @explain：
 */
abstract class BaseFragment<VB : ViewBinding> : BaseBindingFrag<VB>(), TitleBarAction {
    /** 标题栏对象 */
    private var titleBar: ActionBarView? = null

    /**
     * 日志输出标志
     */
    protected val TAG: String = this.javaClass.simpleName
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleBar = getTitleBar()
        // 设置标题栏点击监听
        titleBar?.setOnTitleBarListener(this)

    }

    override fun getTitleBar(): ActionBarView? {
        if (titleBar == null || !isLoading()) {
            titleBar = obtainTitleBar(view as ViewGroup)
        }
        return titleBar
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(messageEvent: MessageEvent) {
        logD(TAG, "BaseFragment-->onMessageEvent()")
    }

    override fun onLeftClick(view: View?) {
    }

    override fun onTitleClick(view: View?) {
    }

    override fun onRightClick(view: View?) {
    }
}