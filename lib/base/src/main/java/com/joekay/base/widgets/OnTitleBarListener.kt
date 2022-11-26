package com.joekay.base.widgets

import android.view.View

/**
 * @author:  JoeKai
 * @date:  2022/11/26
 * @explain： 标题栏点击监听接口
 */
interface OnTitleBarListener {
    /**
     * 左项被点击
     *
     * @param view     被点击的左项View
     */
    fun onLeftClick(view: View?)

    /**
     * 标题被点击
     *
     * @param view     被点击的标题View
     */
    fun onTitleClick(view: View?)

    /**
     * 右项被点击
     *
     * @param view     被点击的右项View
     */
    fun onRightClick(view: View?)
}