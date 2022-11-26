package com.joekay.module_base.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.hjq.demo.action.TitleBarAction
import com.joekay.base.fragment.BaseBindingFrag
import com.joekay.base.widgets.ActionBarView

/**
 * @author:  JoeKai
 * @date:  2022/11/20
 * @explain：
 */
abstract class BaseFragment<VB : ViewBinding> : BaseBindingFrag<VB>(), TitleBarAction {
    /** 标题栏对象 */
    private var titleBar: ActionBarView? = null

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

    override fun onLeftClick(view: View?) {
    }

    override fun onTitleClick(view: View?) {
    }

    override fun onRightClick(view: View?) {
    }
}