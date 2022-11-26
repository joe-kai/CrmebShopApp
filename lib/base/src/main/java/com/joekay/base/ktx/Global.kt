package com.joekay.base.ktx

import android.view.View
import com.joekay.base.aop.SingleClick


/**
 * 批量设置控件点击事件。
 * @param v 点击的控件
 * @param block 处理点击事件回调代码块
 */
@SingleClick
fun View.setSingleClickListener(block: View.() -> Unit) {
    this.setOnClickListener { it.block() }
}

@SingleClick
fun setOnClickListener(vararg v: View?, block: View.() -> Unit) {
    val listener = View.OnClickListener { it.block() }
    v.forEach { it?.setOnClickListener(listener) }
}

@SingleClick
fun setOnLoginClickListener(vararg v: View?, block: View.() -> Unit) {
    val listener = View.OnClickListener {
        //if (DataStoreUtils.getSyncData(TOKEN_KEY, "").isEmpty()) {
        //    GlobalUtil.getString(R.string.not_login_toast).showToast()
        //} else {
        it.block()
        //}
    }
    v.forEach {
        it?.setOnClickListener(listener)
    }
}

/**
 * 批量设置控件点击事件。
 *
 * @param v 点击的控件
 * @param listener 处理点击事件监听器
 */
@SingleClick
fun setOnClickListener(vararg v: View?, listener: View.OnClickListener) {
    v.forEach { it?.setOnClickListener(listener) }
}