package com.joekay.module_base.login

import android.view.View
import com.joekay.module_base.utils.RouterUtils

/**
 * @author:  JoeKai
 * @date:  2023/1/11
 * @explain：
 */


fun loginIntercept(nextAction: () -> Unit) {
    LoginInterceptCoroutinesManager.get()
        .checkLogin({
            RouterUtils.goToLogin()
        }, {
            nextAction()
        })
}

fun View.setLoginClickListener(nextAction: () -> Unit) {
    this.setOnClickListener {
        LoginInterceptCoroutinesManager.get()
            .checkLogin({
                RouterUtils.goToLogin()
            }, {
                nextAction()
            })
    }


}