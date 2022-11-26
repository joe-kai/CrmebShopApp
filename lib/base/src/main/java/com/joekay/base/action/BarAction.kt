package com.joekay.base.action

import com.joekay.base.widgets.ActionBarView

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