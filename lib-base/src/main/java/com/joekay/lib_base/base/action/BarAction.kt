package com.joekay.lib_base.base.action

import com.joekay.lib_base.widgets.ActionBarView

/**
 * @author:  JoeKai
 * @date:  2022/11/20
 * @explain：
 */
interface BarAction {
    fun initActionBar(): ActionBarView?

    fun getActionBarView(): ActionBarView {
        return initActionBar()!!
    }
}