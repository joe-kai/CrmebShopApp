package com.joekay.base.ext

import android.view.View
import android.view.animation.AlphaAnimation
import com.joekay.base.aop.SingleClick


/**
 * 显示view
 */
fun View?.visible() {
    this?.visibility = View.VISIBLE
}

/**
 * 显示view，带有渐显动画效果。
 *
 * @param duration 毫秒，动画持续时长，默认500毫秒。
 */
fun View?.visibleAlphaAnimation(duration: Long = 500L) {
    this?.visibility = View.VISIBLE
    this?.startAnimation(AlphaAnimation(0f, 1f).apply {
        this.duration = duration
        fillAfter = true
    })
}

/**
 * 隐藏view
 */
fun View?.gone() {
    this?.visibility = View.GONE
}

/**
 * 隐藏view，带有渐隐动画效果。
 *
 * @param duration 毫秒，动画持续时长，默认500毫秒。
 */
fun View?.goneAlphaAnimation(duration: Long = 500L) {
    this?.visibility = View.GONE
    this?.startAnimation(AlphaAnimation(1f, 0f).apply {
        this.duration = duration
        fillAfter = true
    })
}

/**
 * 占位隐藏view
 */
fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

fun View.isVisible(visible: Boolean) {
    if (visible) this.visibility = View.VISIBLE else this.visibility = View.GONE
}

/**
 * 占位隐藏view，带有渐隐动画效果。
 *
 * @param duration 毫秒，动画持续时长，默认500毫秒。
 */
fun View?.invisibleAlphaAnimation(duration: Long = 500L) {
    this?.visibility = View.INVISIBLE
    this?.startAnimation(AlphaAnimation(1f, 0f).apply {
        this.duration = duration
        fillAfter = true
    })
}

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


