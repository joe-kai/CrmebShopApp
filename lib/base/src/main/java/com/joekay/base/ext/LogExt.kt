package com.joekay.base.ext

import com.orhanobut.logger.Logger


/**
 * 日志调试工具类。
 *
 * @author
 * @since  2020/4/29
 */

fun mLogV(tag: String, msg: String) {
    Logger.t(tag)
    Logger.v(msg)
}

fun mLogE(tag: String, msg: String) {
    Logger.t(tag)
    Logger.e(msg)
}

fun mLogD(tag: String, msg: String) {
    Logger.t(tag)
    Logger.d(msg)
}

fun mLogV(msg: String, vararg args: Any?) {
    Logger.v(msg, args)
}

fun mLogD(msg: String, vararg args: Any?) {
    Logger.d(msg, args)
}

fun mLogI(msg: String, vararg args: Any?) {
    Logger.i(msg, args)
}

fun mLogW(msg: String, vararg args: Any?) {
    Logger.w(msg, args)
}

fun mLogE(msg: String, vararg args: Any?) {
    Logger.e(msg, args)
}

fun mLogWtf(wtf: String, vararg args: Any?) {
    Logger.wtf(wtf, args)
}

fun mLogJson(json: String) {
    Logger.json(json)
}

fun mLogXml(xml: String) {
    Logger.xml(xml)
}


