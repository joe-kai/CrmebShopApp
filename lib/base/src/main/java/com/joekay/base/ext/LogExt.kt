package com.joekay.base.ext

import android.util.Log
import com.therouter.TheRouter
import timber.log.Timber
import kotlin.String
import kotlin.Throwable
import kotlin.toString


/**
 * 日志调试工具类。
 *
 * @author vipyinzhiwei
 * @since  2020/4/29
 */
private const val VERBOSE = 1
private const val DEBUG = 2
private const val INFO = 3
private const val WARN = 4
private const val ERROR = 5


fun logV(tag: String, msg: String?) {
    Timber.tag(tag).v(msg.toString())
    TheRouter.build("").navigation()
}

fun logD(tag: String, msg: String?) {
    //Timber.tag(tag).d(msg.toString())
    debug(tag, msg!!)
}


fun logI(tag: String, msg: String?) {
    Timber.tag(tag).i(msg.toString())
}

inline fun debug(tag: String, msg: String, block: () -> Unit = {}) {
    if (TheRouter.isDebug) {
        Log.d(":$tag", msg)
        block.invoke()
    } else {
        TheRouter.logCat.invoke(":$tag", msg)
    }
}

fun logW(tag: String, msg: String?, tr: Throwable? = null) {
    if (tr == null) {
        Timber.tag(tag).w(msg.toString())
    } else {
        Timber.tag(tag).w(tr, msg.toString())
    }
}

fun logE(tag: String, msg: String?, tr: Throwable) {
    Timber.tag(tag).e(tr, msg.toString())
}